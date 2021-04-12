package com.hanghae.market.service;

import com.hanghae.market.domain.Follow;
import com.hanghae.market.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FollowService {
    
    @Autowired
    FollowRepository followRepository;
    
    public HashMap<String,Object> getFollow(Long follow_user_id) {
        Follow follow = followRepository.findByFollowUserId(follow_user_id);
        HashMap<String,Object> hashMap = new HashMap<>();
        if(follow == null){
            hashMap.put("check",false);
            return hashMap;
        }else{
            hashMap.put("check",true);
            return hashMap;
        }
    }

    public Follow createFollow(Long follow_user_id) {
        Follow follow = followRepository.findByFollowUserId(follow_user_id);
        if(follow == null){
            Follow newFollow = new Follow();
            followRepository.save(newFollow);
            return newFollow;
        }else{
            return null;
        }
    }

    public Follow deleteFollow(Long follow_user_id) {
        Follow follow = followRepository.findByFollowUserId(follow_user_id);
        if(follow == null){
            return null;
        }else{
            followRepository.deleteById(follow.getId());
            return follow;
        }
    }
}
