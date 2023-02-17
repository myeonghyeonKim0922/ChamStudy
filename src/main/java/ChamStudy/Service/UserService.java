package ChamStudy.Service;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService{
	private final UserRepository userRepository;

	//이메일 중복 체크 실행 및 레파지토리에 저장
	public UserInfo saveUser(UserInfo user) {
		validateDuplicateUser(user); //중복되면 여기서 걸림
		return userRepository.save(user); //중복이 없으면 세이브
	}
	
	//이메일 중복 체크
	private void validateDuplicateUser(UserInfo user) {
		UserInfo findUser = userRepository.findById(user.getId());
				
		
		System.out.println("누나가 찍으라함"+user.getId());
		if(findUser != null) {
			System.out.println("ㅁㄴㅇ"+findUser);
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
}
