package com.PCThanhCong.services.Impl;

import com.PCThanhCong.base.BasePagination;
import com.PCThanhCong.constants.StatusCodeEnum;
import com.PCThanhCong.dto.SignUpDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateUserDTO;
import com.PCThanhCong.entity.RegistrationUserToken;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.repositories.IRegistrationUserTokenRepository;
import com.PCThanhCong.repositories.IUserRepository;
import com.PCThanhCong.services.IUserService;
import com.PCThanhCong.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BasePagination<User, IUserRepository> implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRegistrationUserTokenRepository registrationUserTokenRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository){
        super(userRepository);
    }

    @Override
    public PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification) {
        return this.paginate(page, perPage, specification);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(SignUpDTO signUpDTO) {
        User user = modelMapper.map(signUpDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public void activeUser(String token) {
        RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);
        User user = registrationUserToken.getUser();
        user.setStatus(StatusCodeEnum.ACTIVE);

        userRepository.save(user);

        registrationUserTokenRepository.deleteById(registrationUserToken.getId());
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User update(UpdateUserDTO userUpdateDTO, User currentUser) {
        User updated = modelMapper.map(userUpdateDTO, User.class);
        modelMapper.map(updated, currentUser);
        if(userUpdateDTO.getPassword() != null)
            currentUser.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

}
