package com.PCThanhCong.services.Impl;

import com.PCThanhCong.entity.*;
import com.PCThanhCong.filters.AddCartParams;
import com.PCThanhCong.repositories.ICartRepository;
import com.PCThanhCong.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository repository;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderService orderService;


    @Override
    public Cart getCartByUserId(Integer id) {

        return repository.findCartByUserId(id);
    }

    @Override
    public void addCartItemToCart(AddCartParams params) throws Exception {
        Cart cart = repository.findCartByUserId(params.getUserId());
        Product product = productService.getProductById(params.getProductId());
        if(product.getAmount() == 0)
            throw new Exception("Sản phẩm đã hết hàng");

        CartItem cartItem = null;
        for (CartItem item:cart.getCartItemList()) {
            if(item.getProduct().getId() == product.getId()){
                cartItem = item;
                break;
            }
        }
        if(cartItem != null){
            cartItem.setAmount(cartItem.getAmount() + params.getAmount());
            cartItemService.updateCartItemAmount(cartItem.getId(), cartItem);
        }
        else{
            cartItemService.createCartItem(new CartItem(params.getAmount() , cart , product));
            updateCartAmount(cart.getAmount() + 1 , cart);
        }
    }

    @Override
    public void buyCartItem(Integer userId , Integer cartItemId) {
        Cart cart = repository.findCartByUserId(userId);
        Order order = orderService.getOrderByUserId(userId);
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        OrderItem orderItem = new OrderItem(cartItem.getAmount() ,cart.getUser().getOrder() , cartItem.getProduct());
        orderItemService.createOrderItems(orderItem);
        cartItemService.deleteById(cartItemId , userId);

        updateCartAmount(cart.getCartItemList().size() , cart);
        orderService.updateOrderAmount(order.getOrderItems().size(), order);
        productService.updateProductAmount(cartItem.getProduct() , cartItem.getProduct().getAmount() - cartItem.getAmount());
    }

    @Override
    public void buyListCartItems(Integer userId) {
        Cart cart = repository.findCartByUserId(userId);
        Order order = orderService.getOrderByUserId(userId);
        List<Integer> listId = new ArrayList<>();
        for (CartItem item: cart.getCartItemList()) {
            productService.updateProductAmount(item.getProduct() , item.getProduct().getAmount() - item.getAmount());
            OrderItem orderItem = new OrderItem(item.getAmount() , order , item.getProduct());
            orderItemService.createOrderItems(orderItem);
            listId.add(item.getId());
        }
        cartItemService.deleteByIdIn(listId , userId);
        updateCartAmount(cart.getCartItemList().size() , cart);
        orderService.updateOrderAmount(order.getOrderItems().size(), order);

    }

    @Override
    public Cart updateCartAmount(Integer amount ,  Cart cart) {
        cart.setAmount(amount);
        repository.save(cart);
        return cart;
    }


}
