package com.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dto.CartItemDTO;
import com.ecom.dto.ProductDTO;
import com.ecom.entity.Cart;
import com.ecom.entity.CartItem;
import com.ecom.exception.MultiVendorCartException;
import com.ecom.feign.ProductClient;
import com.ecom.repository.CartRepository;

@Service
public class CartService {

	private final CartRepository cartRepository;

	@Transactional(readOnly = true)
	public Cart getCart(Long userId) {
		return cartRepository.findById(userId).orElse(null);
	}

	private final ProductClient productClient;

	public CartService(CartRepository cartRepository, ProductClient productClient) {
		this.cartRepository = cartRepository;
		this.productClient = productClient;
	}

	@Transactional
	public String addToCart(Long userId, Long productId, int qty) {

		Cart cart = cartRepository.findById(userId).orElseGet(() -> {
			Cart c = new Cart();
			c.setUserId(userId);
			return c;
		});

		ProductDTO product = productClient.getProduct(productId);

		if (!product.isActive() || product.getQuantity() <= 0) {
			throw new RuntimeException("Product not available");
		}

		// 🧠 SINGLE VENDOR RULE
		if (cart.getVendorId() == null) {
			cart.setVendorId(product.getVendorId());
		} else if (!cart.getVendorId().equals(product.getVendorId())) {
			throw new MultiVendorCartException("You can't add products from multiple vendors");
		}

		Optional<CartItem> existingItem = cart.getItems().stream().filter(i -> i.getProductId().equals(productId))
				.findFirst();

		if (existingItem.isPresent()) {

			CartItem item = existingItem.get();
			int newQty = item.getQuantity() + qty;

			item.setQuantity(Math.min(newQty, product.getQuantity()));

			cartRepository.save(cart);

			return "Product quantity updated in cart";

		} else {
			CartItem item = new CartItem();
			item.setProductId(productId);
			item.setVendorId(product.getVendorId());
			item.setPrice(product.getPrice());
			item.setQuantity(Math.min(qty, product.getQuantity()));

			cart.getItems().add(item);
			cartRepository.save(cart);

			return "Product added to cart from vendor " + product.getVendorId();
		}
	}

	@Transactional
	public void clearCart(Long userId) {
		Cart cart = cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
		cart.getItems().clear();
		cart.setVendorId(null);
		cartRepository.save(cart);
	}

	@Transactional
	public String updateItemQuantity(Long userId, Long productId, int qty) {

		if (qty <= 0) {
			throw new RuntimeException("Quantity must be at least 1");
		}

		Cart cart = cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

		ProductDTO product = productClient.getProduct(productId);

		CartItem item = cart.getItems().stream().filter(i -> i.getProductId().equals(productId)).findFirst()
				.orElseThrow(() -> new RuntimeException("Product not found in cart"));

		int finalQty = Math.min(qty, product.getQuantity());
		item.setQuantity(finalQty);

		cartRepository.save(cart);

		return "Cart item quantity updated";
	}

	@Transactional
	public String removeItem(Long userId, Long productId) {

		Cart cart = cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

		boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

		if (!removed) {
			throw new RuntimeException("Product not found in cart");
		}

		if (cart.getItems().isEmpty()) {
			cart.setVendorId(null);
		}

		cartRepository.save(cart);

		return "Product removed from cart";
	}

	@Transactional(readOnly = true)
	public List<CartItemDTO> getCartItems(Long userId) {

		Cart cart = cartRepository.findById(userId).orElse(null);

		if (cart == null || cart.getItems().isEmpty()) {
			return List.of();
		}

		return cart.getItems().stream().map(item -> {
			CartItemDTO dto = new CartItemDTO();
			dto.setProductId(item.getProductId());
			dto.setVendorId(item.getVendorId());
			dto.setQuantity(item.getQuantity());
			dto.setPrice(item.getPrice());
			return dto;
		}).toList();
	}

}
