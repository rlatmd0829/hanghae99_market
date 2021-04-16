package com.hanghae.market.service;

import com.hanghae.market.model.Follow;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.FollowRepository;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    // 팔로우 조회
    public HashMap<String,Object> getFollow(Long followUserId, Long userId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(
                () -> new IllegalArgumentException("팔로우한 사용자를 찾을 수 없습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Follow follow = followRepository.findByFollowUserIdAndUserId(followUserId, userId);

        // 내 팔로우 수가 아니라 상대방 팔로우 수이다.
        List<Follow> followCount = followRepository.findByFollowUserId(followUserId); // 내가 지금 로그인한 사용자의 팔로우 카운트를 찾으려면 userId넣으면 될 것 같음
        Integer Count = followCount.size();
        HashMap<String,Object> hashMap = new HashMap<>();

        if(follow == null){
            hashMap.put("check",false);
            hashMap.put("followCount", Count);
            return hashMap;
        }else{
            hashMap.put("check",true);
            hashMap.put("followCount", Count);
            return hashMap;
        }
    }

    // 팔로우 하기
    public Follow createFollow(Long followUserId, Long userId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(
                () -> new IllegalArgumentException("팔로우한 사용자를 찾을 수 없습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Follow follow = followRepository.findByFollowUserIdAndUserId(followUserId, userId);
        if(follow == null){
            Follow newFollow = new Follow();
            newFollow.addUser(user);
            newFollow.addFollowUser(followUser);
            followRepository.save(newFollow);
            return newFollow;
        }else{
            return null;
        }
    }

    // 팔로우 취소
    public Follow deleteFollow(Long followUserId, Long userId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(
                () -> new IllegalArgumentException("팔로우한 사용자를 찾을 수 없습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Follow follow = followRepository.findByFollowUserIdAndUserId(followUserId, userId);
        if(follow == null){
            return null;
        }else{
            followRepository.deleteById(follow.getId());
            return follow;
        }
    }
}
