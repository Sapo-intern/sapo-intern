package sapo.intern.mock.carstore.user.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.user.dto.request.UserUpdateRequest;
import sapo.intern.mock.carstore.user.exception.AppException;
import sapo.intern.mock.carstore.user.exception.ErrorCode;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepo userRepo;

    public Page<User> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepo.findAll(pageable);
    }

    public User getUser(String id){
        return userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA));
    }

    public void deleteUser(String userId){
        userRepo.deleteById(userId);
    }

    public User updateUser(String userId, UserUpdateRequest request){
        User user = getUser(userId);

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAge(request.getAge());

        return userRepo.save(user);
    }

    public List<User> searchByNameOrPhoneNumber(String query) {
        return userRepo.findByNameOrPhone(query, query);
    }
}

