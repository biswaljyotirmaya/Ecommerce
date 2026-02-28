package com.ecom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dto.CartItemDTO;
import com.ecom.dto.OrderItemResponseDTO;
import com.ecom.dto.OrderResponseDTO;
import com.ecom.entity.CheckoutSession;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import com.ecom.feign.CartClient;
import com.ecom.feign.InventoryClient;
import com.ecom.repository.CheckoutSessionRepository;
import com.ecom.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final CheckoutSessionRepository checkoutSessionRepository;
	private final CartClient cartClient;
	private final InventoryClient inventoryClient;

	public OrderService(OrderRepository orderRepository, CheckoutSessionRepository checkoutSessionRepository,
			CartClient cartClient, InventoryClient inventoryClient) {

		this.orderRepository = orderRepository;
		this.checkoutSessionRepository = checkoutSessionRepository;
		this.cartClient = cartClient;
		this.inventoryClient = inventoryClient;
	}

	@Transactional
	public Order placeOrder(Long userId) {

		// 1️⃣ Fetch verified checkout session
		CheckoutSession session = checkoutSessionRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Checkout session not found"));

		if (!session.isVerified()) {
			throw new RuntimeException("OTP not verified");
		}

		if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Checkout session expired");
		}

		// 2️⃣ Fetch cart
		List<CartItemDTO> cartItems = cartClient.getCart();

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		// 3️⃣ Create Order
		Order order = new Order();
		order.setUserId(userId);
		order.setVendorId(session.getVendorId());
		order.setTotalAmount(session.getTotalAmount());
		order.setStatus("CREATED");
		order.setCreatedAt(LocalDateTime.now());

		// 4️⃣ Create OrderItems
		for (CartItemDTO item : cartItems) {

			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPriceSnapshot(item.getPrice());
			orderItem.setOrder(order);

			order.getItems().add(orderItem);
		}

		Order savedOrder = orderRepository.save(order);

		// 5️⃣ Reduce inventory
		for (CartItemDTO item : cartItems) {
			inventoryClient.reduce(item.getProductId(), item.getQuantity());
		}

		// 6️⃣ Cleanup
		cartClient.clear();
		checkoutSessionRepository.deleteByUserId(userId);

		return savedOrder;
	}

	@Transactional
	public void completeOrder(Long orderId, Long vendorId) {

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		// 🔐 Ownership check
		if (!order.getVendorId().equals(vendorId)) {
			throw new RuntimeException("Access denied: Not your order");
		}

		if ("COMPLETED".equals(order.getStatus())) {
			throw new RuntimeException("Order already completed");
		}

		order.setStatus("COMPLETED");
		orderRepository.save(order);
	}

	private OrderResponseDTO mapToDto(Order order) {

		OrderResponseDTO dto = new OrderResponseDTO();
		dto.setOrderId(order.getId());
		dto.setUserId(order.getUserId());
		dto.setVendorId(order.getVendorId());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setStatus(order.getStatus());
		dto.setCreatedAt(order.getCreatedAt());

		dto.setItems(order.getItems().stream().map(item -> {
			OrderItemResponseDTO i = new OrderItemResponseDTO();
			i.setProductId(item.getProductId());
			i.setQuantity(item.getQuantity());
			i.setPrice(item.getPriceSnapshot());
			return i;
		}).toList());

		return dto;
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getOrdersForVendor(Long vendorId) {

		return orderRepository.findByVendorId(vendorId).stream().map(this::mapToDto).toList();
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getOrdersForConsumer(Long userId) {

		return orderRepository.findByUserId(userId).stream().map(this::mapToDto).toList();
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getCompletedSalesForVendor(Long vendorId) {

		return orderRepository.findByVendorIdAndStatus(vendorId, "COMPLETED").stream().map(this::mapToDto).toList();
	}

	@Transactional
	public void cancelOrdersForVendor(Long vendorId) {

		List<Order> orders = orderRepository.findByVendorId(vendorId);

		for (Order order : orders) {
			if (!"COMPLETED".equals(order.getStatus())) {
				order.setStatus("CANCELLED");
			}
		}

		orderRepository.saveAll(orders);
	}

	@Transactional
	public void cancelOrdersForConsumer(Long userId) {

		List<Order> orders = orderRepository.findByUserId(userId);

		for (Order order : orders) {
			if (!"COMPLETED".equals(order.getStatus())) {
				order.setStatus("CANCELLED");
			}
		}

		orderRepository.saveAll(orders);
	}

}
