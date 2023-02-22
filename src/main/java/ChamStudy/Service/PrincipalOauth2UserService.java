package ChamStudy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.UserRepository;

//구글 후처리 해주는 서비스
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	@Autowired
	private UserRepository userRepository;
	
	
	
	//구글로 부터 받은 userrequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:" + userRequest.getClientRegistration()); //어떤 OAuth로 로그인 했는지 확인 가능
		System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue()); //토큰 정보 확인가능, 지금은 필요없음
		System.out.println("getClientRegistration:" + userRequest.getClientRegistration().getClientId());
		// 구글로가인 버튼 클릭 -> 구글 로그인 창-> 로그인 완료 -> code를 리턴(Oauth-cient라이브러리가 받아줌) -> AccessToken 요청
		//userRequest 정보를 받고 loadUser 함수를 호출 후 구글로 부터 회원 프로필을 받아줌
		System.out.println("getAttributes:" + super.loadUser(userRequest).getAttributes());
//===========================================================================================================================
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		String username = oauth2User.getAttribute("name");
		String email = oauth2User.getAttribute("email");
		String password = "123456789";
		String role = "USER";
		String regDate = "654";
		String phone = "asd0";
		
		
		UserInfo userEntity =  userRepository.findByemail(email);
		
		if(userEntity == null) {
			userEntity = UserInfo.builder()
					.name(username)
					.email(email)
					.password(password)
					.phone(phone)
					.role(role)
					.regDate(regDate)
					.build();
			userRepository.save(userEntity);
		}else {
			
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
}
