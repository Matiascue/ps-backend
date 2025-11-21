package com.example.users.services.imp;

import com.example.users.dto.FriendInfoDto;
import com.example.users.dto.post.FriendPostDto;
import com.example.users.entity.FriendEntity;
import com.example.users.entity.UserEntity;
import com.example.users.repository.FriendRepository;
import com.example.users.repository.UserRepository;
import com.example.users.services.ExternService;
import com.example.users.services.FriendService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImp implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ExternService externService;
    public FriendServiceImp(FriendRepository friendRepository,UserRepository userRepository,
     ExternService externService){
        this.friendRepository=friendRepository;
        this.userRepository=userRepository;
        this.externService=externService;
    }

    @Override
    public FriendPostDto sendFriendRequest(FriendPostDto friendPostDto) {
        UserEntity userEntity=this.userRepository.findById(friendPostDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        UserEntity friendUser=this.userRepository.findById(friendPostDto.getFriendId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        this.friendRequestExists(friendPostDto.getUserId(),friendPostDto.getFriendId());
        FriendEntity friendEntity=new FriendEntity();
        friendEntity.setFriend(friendUser);
        friendEntity.setUser(userEntity);
        friendEntity.setRequestDate(LocalDateTime.now());
        friendEntity.setStatus("PENDING");
        this.friendRepository.save(friendEntity);
        this.externService.sendFriendRequestMail(friendEntity.getFriend().getEmail(),
                friendEntity.getUser().getId(),friendEntity.getUser().getUsername(),friendEntity.getFriend().getUsername());
        return friendPostDto;
    }

    @Override
    public void acceptFriendRequest(Long friendId) {
        FriendEntity friendEntity=this.friendRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("La solicitud no se encontro"));
        friendEntity.setStatus("ACCEPTED");
        this.friendRepository.save(friendEntity);
    }

    @Override
    public void cancelFriendRequest(Long friendId) {
        FriendEntity friendEntity=this.friendRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("La solicitud no se encontro"));
        friendEntity.setStatus("REJECTED");
        this.friendRepository.save(friendEntity);
    }

    @Override
    public void sendListingEmail(Long userId) {
        List<FriendEntity>friendEntities=this.friendRepository.findAllByUserIdAndStatus(userId,"ACCEPTED");
        for(FriendEntity f:friendEntities) {
            if (f.getFriend().isActive() || f.getUser().isActive()) {
                this.externService.sendListingFriendsMail(f.getFriend().getEmail(), f.getUser().getId(), f.getFriend().getUsername(), f.getUser().getUsername());
            }
        }
    }

    @Override
    public void deleteFriend(Long userId,Long friendId) {
        this.friendRepository.findByUserIdAndFriendIdAndStatus(userId, friendId,"ACCEPTED")
                .ifPresent(friend -> {
                    friend.setStatus("CANCELLED");
                    this.friendRepository.save(friend);
                });

        this.friendRepository.findByUserIdAndFriendIdAndStatus(friendId, userId,"ACCEPTED")
                .ifPresent(friend -> {
                    friend.setStatus("CANCELLED");
                    this.friendRepository.save(friend);
                });
    }

    @Override
    public boolean getFriendId(Long userId, Long userFriendId) {
        return this.friendRepository.findByUserIdAndFriendIdAndStatus(userId,userFriendId,"ACCEPTED").isPresent()
                ||friendRepository.findByUserIdAndFriendIdAndStatus(userFriendId,userId,"ACCEPTED").isPresent();
    }

    @Override
    public List<FriendInfoDto> getAllFriendsByUserId(Long userId) {
        List<FriendEntity>friendEntities=this.friendRepository.findAllByUserIdAndStatus(userId,"ACCEPTED");
        List<FriendEntity>friends2=this.friendRepository.findAllByFriendIdAndStatus(userId,"ACCEPTED");
        List<FriendInfoDto>friendInfoDtos=new ArrayList<>();
        for(FriendEntity f:friendEntities){
            if(f.getFriend().isActive()||f.getUser().isActive()) {
                FriendInfoDto friend = new FriendInfoDto();
                friend.setFriendId(f.getFriend().getId());
                friend.setFriendUsername(f.getFriend().getUsername());
                friend.setId(f.getId());
                friend.setUserId(f.getUser().getId());
                friend.setRequestDate(f.getRequestDate());
                friendInfoDtos.add(friend);
            }
        }
        for(FriendEntity f:friends2){
            if(f.getFriend().isActive()||f.getUser().isActive()) {
                FriendInfoDto friend = new FriendInfoDto();
                friend.setFriendId(f.getUser().getId());
                friend.setFriendUsername(f.getUser().getUsername());
                friend.setId(f.getId());
                friend.setUserId(f.getFriend().getId());
                friend.setRequestDate(f.getRequestDate());
                friendInfoDtos.add(friend);
            }
        }
        return friendInfoDtos;
    }

    @Override
    public List<FriendInfoDto> getAllFriendsRequestByUserId(Long userId) {
        List<FriendEntity>friendEntities=this.friendRepository.findAllByFriendIdAndStatus(userId,"PENDING");
        List<FriendInfoDto>friendInfoDtos=new ArrayList<>();
        for(FriendEntity f:friendEntities){
            FriendInfoDto friend=new FriendInfoDto();
            friend.setFriendId(f.getUser().getId());
            friend.setFriendUsername(f.getUser().getUsername());
            friend.setId(f.getId());
            friend.setUserId(f.getFriend().getId());
            friend.setRequestDate(f.getRequestDate());
            friendInfoDtos.add(friend);
        }
        return friendInfoDtos;
    }

    private void friendRequestExists(Long userId,Long friendId){
        Optional<FriendEntity> friendEntity=this.friendRepository.findByUserIdAndFriendId(userId,friendId);
        if(friendEntity.isPresent()){
            if(friendEntity.get().getStatus().equals("ACCEPTED") ||
                    friendEntity.get().getStatus().equals("PENDING")){
                throw new IllegalArgumentException("El usuario ya es su amigo o aun no ha aceptado");
            }
        }
    }
}
