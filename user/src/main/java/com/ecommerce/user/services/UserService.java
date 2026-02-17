package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRepsonse;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

//    private Long ID= 1L;

    private final UserRepository userRepository;

    public List<User> userList = new ArrayList<>();


    public List<UserRepsonse> fetchAllUser() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(x -> this.mapToUserResponse(x) )
                .collect(Collectors.toList());
    }

    public void createUser(UserRequest userRequest) {
//        user.setId( ID++ );
        User user = new User();
        updateUserFromRequest(user, userRequest);
       userRepository.save(user);

    }


    public Optional<UserRepsonse> fetchUser(String id) {

        return userRepository.findById(String.valueOf(id))
                .map(x-> this.mapToUserResponse(x));

    }

    public boolean UpdateUser(String id, UserRequest updateUserRequest) {
      return userRepository.findById(String.valueOf(id))
              .map( existUser -> {
                  updateUserFromRequest(existUser, updateUserRequest);
                  userRepository.save(existUser);
                  return true;
              }).orElse(false);
    }

    private UserRepsonse mapToUserResponse(User user) {
        UserRepsonse response = new UserRepsonse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
     return response;
    }


    private void updateUserFromRequest(User user, UserRequest userRequest) {
       user.setFirstName(userRequest.getFirstName());
       user.setLastName(userRequest.getLastName());
       user.setEmail(userRequest.getEmail());
       user.setPhone(userRequest.getPhone());

       if(userRequest.getAddress()!= null) {
           Address address = new Address();
           address.setStreet(userRequest.getAddress().getStreet());
           address.setCity(userRequest.getAddress().getCity());
           address.setState(userRequest.getAddress().getState());
           address.setZipcode(userRequest.getAddress().getZipcode());
           address.setCountry(userRequest.getAddress().getCountry());
           user.setAddress(address);
       }
    }

}
