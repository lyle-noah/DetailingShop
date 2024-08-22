package com.green3rd.DetailingShop.UserLike;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLikesService {

    private final UserLikesRepository userLikesRepository;

    public Optional<UserLikes> findByUserAndProduct(User user, Product product) {
        return userLikesRepository.findByUserAndProduct(user, product);
    }
}
