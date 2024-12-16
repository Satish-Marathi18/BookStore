package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.CartRequestDTO;
import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.Cart;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.repo.BookRepo;
import com.bridgelabz.BookStore.repo.CartRepo;
import com.bridgelabz.BookStore.repo.UserRepository;
import com.bridgelabz.BookStore.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    private UserRepository userRepo;
    private BookRepo bookRepo;
    public CartServiceImpl(CartRepo cartRepo, UserRepository userRepo, BookRepo bookRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }


    @Override
    public CartResponseDTO addToCart(Long userId, CartRequestDTO cartRequestDTO) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Book book = bookRepo.findById(cartRequestDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        Cart isCartPresent = cartRepo.findByBookAndUser(book, user).orElse(null);
        if(user != null && book != null && isCartPresent == null) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setBook(book);
            cart.setQuantity(cartRequestDTO.getQuantity());
            cart.setTotalPrice(cartRequestDTO.getQuantity() * book.getBookPrice());
            cart = cartRepo.save(cart);
            return mapToResponseDTO(cart);
        }
        if(isCartPresent != null) {
            isCartPresent.setQuantity(isCartPresent.getQuantity() + cartRequestDTO.getQuantity());
            isCartPresent.setTotalPrice(isCartPresent.getTotalPrice() + (cartRequestDTO.getQuantity() * book.getBookPrice()));
            return mapToResponseDTO(cartRepo.save(isCartPresent));
        }

        else if(user == null)
            throw new RuntimeException("User Not Found");
        else
            throw new RuntimeException("Book Not Found");
    }

    @Override
    public String removeCartByUser(Long userId, Long cartId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Cart userCart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if(!user.equals(userCart.getUser()) || user == null) {
            throw new RuntimeException("This cart is not belong to this user");
        }
        cartRepo.deleteById(userCart.getCartId());
        return "Cart removed successfully";
    }

    @Override
    public String removeFromCart(Long cartId) {
        Cart userCart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepo.deleteById(userCart.getCartId());
        return "Cart removed successfully";
    }

    @Override
    public CartResponseDTO updateQuantity(Long userId, Long cartId, Long quantity) {
        Cart userCart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if(userId != userCart.getUser().getId()) {
            throw new RuntimeException("Invalid User");
        }
        userCart.setQuantity(quantity);
        userCart.setTotalPrice(quantity * userCart.getBook().getBookPrice());
        userCart = cartRepo.save(userCart);
        return mapToResponseDTO(userCart);
    }

    @Override
    public List<CartResponseDTO> getAllCartItemsForUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<Cart> userCartItems = cartRepo.findByUser(user);
        List<CartResponseDTO> cartResponseDTOList = userCartItems.stream()
                .map(userCartItem -> mapToResponseDTO(userCartItem)).toList();
        return cartResponseDTOList;
    }

    @Override
    public List<CartResponseDTO> getAllCartItems() {
        List<Cart> allCartItems = cartRepo.findAll();
        List<CartResponseDTO> cartResponseDTOList = allCartItems.stream()
                .map(userCartItem -> mapToResponseDTO(userCartItem)).toList();
        return cartResponseDTOList;
    }


    private CartResponseDTO mapToResponseDTO(Cart cart) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setUser(cart.getUser());
        cartResponseDTO.setQuantity(cart.getQuantity());
        cartResponseDTO.setTotalPrice(cart.getTotalPrice());
        cartResponseDTO.setBook(cart.getBook());
        return cartResponseDTO;
    }
}
