package com.EcomMic.App.service;

import com.EcomMic.App.dto.AddressDto;
import com.EcomMic.App.dto.UserRequest;
import com.EcomMic.App.dto.UserResponse;
import com.EcomMic.App.model.Address;
import com.EcomMic.App.model.User;
import com.EcomMic.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

   public List<UserResponse> findAll(){
       return userRepository.findAll().stream().map(this::mapUserToUserResponse).collect(Collectors.toList());
   }

   public void save(UserRequest userRequest){
       User user = new User();
       updateUserFromRequest(user,userRequest);
       userRepository.save(user);
   }

    public Optional<UserResponse> findById(Long id){
       return userRepository.findById(id).map(this::mapUserToUserResponse);
   }

   public Boolean updateUser(Long id, UserRequest userRequest){
       return userRepository.findById(id).map(existingUser -> {
           updateUserFromRequest(existingUser, userRequest);
           userRepository.save(existingUser);
           return true;
       }).orElse(false);
   }

   private UserResponse mapUserToUserResponse(User user){
      UserResponse userResponse = new UserResponse();
      userResponse.setId(String.valueOf(user.getId()));
      userResponse.setFirstName(user.getFirstName());
      userResponse.setLastName(user.getLastName());
      userResponse.setEmail(user.getEmail());
      userResponse.setPhone(user.getPhone());
      userResponse.setRole(user.getRole());

      if(user.getAddress() != null){
          AddressDto addressDto = new AddressDto();
          addressDto.setStreet(user.getAddress().getStreet());
          addressDto.setCity(user.getAddress().getCity());
          addressDto.setCountry(user.getAddress().getCountry());
          addressDto.setZip(user.getAddress().getZip());
          addressDto.setState(user.getAddress().getState());
      }

      return userResponse;
   }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
       user.setFirstName(userRequest.getFirstName());
       user.setLastName(userRequest.getLastName());
       user.setEmail(userRequest.getEmail());
       user.setPhone(userRequest.getPhone());

       if(user.getAddress() != null){
           Address address = new Address();
           address.setCity(userRequest.getAddressDto().getCity());
           address.setCountry(userRequest.getAddressDto().getCountry());
           address.setStreet(userRequest.getAddressDto().getStreet());
           address.setZip(userRequest.getAddressDto().getZip());
           address.setState(userRequest.getAddressDto().getState());
           user.setAddress(address);
       }
    }

}
