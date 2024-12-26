package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.BookResponseDTO;
import com.bridgelabz.BookStore.dto.OrderRequestDTO;
import com.bridgelabz.BookStore.dto.OrdersResponseDTO;
import com.bridgelabz.BookStore.entity.*;
import com.bridgelabz.BookStore.mapper.Mapper;
import com.bridgelabz.BookStore.repo.*;
import com.bridgelabz.BookStore.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {
    private BookRepo bookRepo;
    private OrderRepo orderRepo;
    private CartRepo cartRepo;
    private UserRepository userRepo;
    private OrderedBooksRepo orderedBooksRepo;

    public OrdersServiceImpl(OrderRepo orderRepo, CartRepo cartRepo, UserRepository userRepo, BookRepo bookRepo, OrderedBooksRepo orderedBooksRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.orderedBooksRepo = orderedBooksRepo;
    }


    @Override
    public OrdersResponseDTO PlaceOrderByCartId(Long userId, OrderRequestDTO orderRequestDTO, Long cartId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Cart userCart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        Book book = bookRepo.findById(userCart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        Boolean isUserCart = cartRepo.existsByUser(user);
        if(!isUserCart) {
            throw new RuntimeException("Cart Not Found");
        }
        if(userCart.getQuantity() > book.getQuantity()) {
            throw new RuntimeException("Only "+book.getQuantity()+" "+book.getBookName()+" books are available");
        }
        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setQuantity(userCart.getQuantity());
        order.getBook().add(book);
        order.setAddress(orderRequestDTO.getAddress());
        order.setCancel(false);
        order.setPrice(userCart.getTotalPrice());
        order = orderRepo.save(order);
        OrderedBooks orderedBook = saveToOrderedBooks(userCart,book,order);
        order.getOrderedBooks().add(orderedBook);
        orderRepo.save(order);
        book.setQuantity(book.getQuantity() - userCart.getQuantity());
        bookRepo.save(book);
        return mapToOrderResponseDTO(order);
    }

    @Override
    public OrdersResponseDTO orderAllCartItems(Long userId, OrderRequestDTO orderRequestDTO) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<Cart> userCartItems = cartRepo.findByUser(user);
        Map<Book, Long> cartBooksWithQuantity = new HashMap<>();
        userCartItems.forEach(cart -> cartBooksWithQuantity.put(cart.getBook(), cart.getQuantity()));
        Set<Book> books = cartBooksWithQuantity.keySet();
        books.forEach(book -> {
            if(book.getQuantity() < cartBooksWithQuantity.get(book)) {
                throw new RuntimeException("Only "+book.getQuantity()+" "+book.getBookName()+" books are available");
            }
        });
        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setCancel(false);
        order.getBook().addAll(cartBooksWithQuantity.keySet());
        order.setAddress(orderRequestDTO.getAddress());
        order.setQuantity(userCartItems.stream().mapToLong(cart -> cart.getQuantity()).sum());
        order.setPrice(userCartItems.stream().mapToDouble(cart -> cart.getTotalPrice()).sum());
        Orders orderToMap =orderRepo.save(order);
        List<OrderedBooks> orderedBooksList = userCartItems.stream()
                        .map(cart -> {
                            return saveToOrderedBooks(cart, cart.getBook(),orderToMap);
                        }).toList();
        order.getOrderedBooks().addAll(orderedBooksList);
        changeBooksQuantityAfterOrder(books, cartBooksWithQuantity);
        return mapToOrderResponseDTO(orderRepo.save(order));
    }

    @Override
    public String cancelOrder(Long userId, Long orderId) {
        Orders order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Boolean orderContainUser = orderRepo.existsByUser(user);
        if(!orderContainUser) {
            throw new RuntimeException("Order does not belong to this user");
        }
        if(order.getCancel()) {
            throw new RuntimeException(" Order is already cancelled");
        }
        List<OrderedBooks> orderedBooksList = orderedBooksRepo.findByOrder(order);
        orderedBooksList.forEach(orderedBook -> {
            Book book = orderedBook.getBook();
            book.setQuantity(book.getQuantity() + orderedBook.getQuantity());
            bookRepo.save(book);
        });
        order.setCancel(true);
        orderRepo.save(order);
        return "Order cancelled successfully";
    }

    @Override
    public List<OrdersResponseDTO> getAllOrders() {
        List<Orders> orders = orderRepo.findByCancel(false);
        return orders.stream()
                .map(order -> mapToOrderResponseDTO(order)).toList();
    }

    @Override
    public List<OrdersResponseDTO> getUserOrders(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<Orders> userOrders = orderRepo.findByUser(user);
        return userOrders.stream().map(order -> mapToOrderResponseDTO(order)).toList();
    }

    private OrdersResponseDTO mapToOrderResponseDTO(Orders order) {
        OrdersResponseDTO dto = new OrdersResponseDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setQuantity(order.getQuantity());
        dto.setAddress(order.getAddress());
        dto.setPrice(order.getPrice());
        dto.getBook().addAll(mapBookToDTO(order.getBook()));
        dto.setCancel(order.getCancel());
        dto.setUser(order.getUser());
        return dto;
    }

    private List<BookResponseDTO> mapBookToDTO(List<Book> books) {
        return books.stream()
                    .map(book -> {
                        return Mapper.mapBookToResponseDTO(book);
                    }).toList();
    }

    private OrderedBooks saveToOrderedBooks(Cart userCart, Book book, Orders order) {
        OrderedBooks orderedBook = new OrderedBooks();
        orderedBook.setBook(book);
        orderedBook.setQuantity(userCart.getQuantity());
        orderedBook.setOrder(order);
        return orderedBooksRepo.save(orderedBook);
    }

    private void changeBooksQuantityAfterOrder(Set<Book> books, Map<Book, Long> cartBooksWithQuantity) {
        books.forEach(book -> {
            book.setQuantity(book.getQuantity() - cartBooksWithQuantity.get(book));
            bookRepo.save(book);
        });
    }
}

