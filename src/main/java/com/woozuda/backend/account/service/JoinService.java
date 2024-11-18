package com.woozuda.backend.account.service;

import com.woozuda.backend.account.dto.JoinDTO;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){

        //이미 있는 계정이면 만들 수 없습니다
        if(userRepository.existsByUsername(joinDTO.getUsername())){
            return;
        }

        //비밀번호 암호화(bcrypt)
        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        //dto -> entity 변환
        UserEntity data = UserEntity.transEntity(joinDTO);

        //레포지터리에 entity를 저장합니다
        userRepository.save(data);
    }

}
