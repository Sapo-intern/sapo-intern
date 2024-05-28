package sapo.intern.mock.carstore.user.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.user.dto.request.UserUpdateRequest;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepo userRepo;

//    @PreAuthorize("hasRole('MANAGER')")
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

    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

//        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAge(request.getAge());
//        user.setUrlImage(request.getUrlImage());

        return userRepo.save(user);
    }

    public List<User> getUserByKeyword(String keyword) {
        List<User> user = userRepo.findByKeywordContainingIgnoreCase(keyword);

        if(user.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return user;
    }

}

