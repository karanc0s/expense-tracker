package com.karan.authservice.service;

import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsIMPL implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsIMPL.class);

    @Autowired
    public UserDetailsIMPL(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may be case-sensitive, or case-insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Cloud not found the user"+username);
        }
        return new FullUserDetails(user.get());
    }


    public Optional<UserInfo> checkIfUserExists(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean signUpUser(UserInfoDTO userInfoDTO){

        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(checkIfUserExists(userInfoDTO.getUsername()).isPresent()){
            return false;
        }

        String userId = UUID.randomUUID().toString();
        UserInfo info = new UserInfo(
                userId,
                userInfoDTO.getUsername(),
                userInfoDTO.getPassword(),
                new HashSet<>()
        );
        userRepository.save(info);

        // TODO ::: pushEventToQueue
        return true;
    }
}
