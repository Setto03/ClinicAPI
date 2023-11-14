package project.clinic.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.clinic.model.Role;
import project.clinic.model.User;
import project.clinic.repository.UserRepository;
import project.clinic.security.JwtService;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.findUserByEmail(request.getEmail()).isPresent()) {
            return AuthenticationResponse.builder()
                    .message("Người dùng đã đăng ký.")
                    .build();
        }

        var user = User.builder()
               .email(request.getEmail())
               .password(passwordEncoder.encode(request.getPassword()))
               .createAt(String.valueOf(new Date(System.currentTimeMillis())))
                .fullName(request.getName())
                .gender(request.getGender())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();

        var jwtToken = jwtService.generateToken(user);

        repository.save(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Đăng ký thành công, chờ xác nhận.")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         request.getEmail(),
                         request.getPassword())
        );

        var user = repository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                 .message("Xác thực thành công.")
                 .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        Optional<User> check = repository.findUserByEmail(request.getEmail());

        if (check.isEmpty()) {
            return  AuthenticationResponse.builder()
                    .message("Không tìm thấy tài khoản")
                    .build();
        }

        User user = check.get();
        if (!user.isEnabled()) {
            return AuthenticationResponse.builder()
                    .message("Tài khoản đã bị khóa. Lý do: " + user.getLocked())
                    .build();
        }


        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Đăng nhập thành công.")
                .build();
    }

}
