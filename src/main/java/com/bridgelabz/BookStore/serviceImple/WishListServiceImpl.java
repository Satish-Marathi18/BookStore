package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.BookResponseDTO;
import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.dto.WishListResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.Cart;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.entity.WishList;
import com.bridgelabz.BookStore.repo.BookRepo;
import com.bridgelabz.BookStore.repo.CartRepo;
import com.bridgelabz.BookStore.repo.UserRepository;
import com.bridgelabz.BookStore.repo.WishListRepo;
import com.bridgelabz.BookStore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepo wishListRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    UserRepository userRepo;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public WishListResponseDTO addToWishList(Long userId, Long bookId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(wishListRepo.existsByBook(book)) {
            throw new IllegalArgumentException("Book already exists in WishList");
        }
        WishList wishList = new WishList();
        wishList.setBook(book);
        wishList.setUser(user);
        wishList.setAvailable(book.getQuantity()>0);
        return mapToDTO(wishListRepo.save(wishList));
    }

    @Override
    public List<WishListResponseDTO> getUserWishList(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<WishList> userWishList = wishListRepo.findByUser(user);
        if(userWishList.isEmpty()) {
            throw new IllegalArgumentException("User doesnot have any wish list");
        }
        return userWishList.stream().map(wishList -> mapToDTO(wishList)).collect(Collectors.toList());
    }

    @Override
    public String removeFromWishList(Long userId, Long bookId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        WishList userWishList = wishListRepo.findByBookAndUser(book,user).orElseThrow(() -> new IllegalArgumentException("You don't have this book in wishlist"));
        wishListRepo.delete(userWishList);
        return "Removed from WishList";
    }

    @Override
    public CartResponseDTO addToCart(Long userId, Long wishListId) {
        WishList wishList = wishListRepo.findById(wishListId).orElseThrow(() -> new IllegalArgumentException("WishList not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(!wishListRepo.existsByIdAndUser(wishListId, user)) {
            throw new IllegalArgumentException("WishList doesnot exist");
        }
        Cart cart = new Cart();
        cart.setQuantity(1l);
        cart.setUser(user);
        cart.setBook(wishList.getBook());
        cart.setTotalPrice(wishList.getBook().getBookPrice());
        return mapCartToDTO(cartRepo.save(cart));
    }

    private CartResponseDTO mapCartToDTO(Cart cart) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setUser(cart.getUser());
        cartResponseDTO.setQuantity(cart.getQuantity());
        cartResponseDTO.setTotalPrice(cart.getTotalPrice());
        cartResponseDTO.setBook(cart.getBook());
        return cartResponseDTO;
    }

    private WishListResponseDTO mapToDTO(WishList save) {
        WishListResponseDTO wishList = new WishListResponseDTO();
        wishList.setBook(mapBookToDTO(save.getBook()));
        wishList.setUser(save.getUser());
        wishList.setIsAvailable(save.getAvailable()?"Available":"Currently not available");
        return wishList;
    }

    private BookResponseDTO mapBookToDTO(Book book) {
                    BookResponseDTO dto = new BookResponseDTO();
                    dto.setId(book.getId());
                    dto.setBookName(book.getBookName());
                    dto.setBookAuthor(book.getBookAuthor());
                    dto.setBookPrice(book.getBookPrice());
                    dto.setQuantity(book.getQuantity());
                    dto.setBookLogo("Book Logo");
                    dto.setBookDescription(book.getBookDescription());
                    return dto;
    }
}
