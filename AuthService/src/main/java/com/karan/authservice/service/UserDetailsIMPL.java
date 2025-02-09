package com.karan.authservice.service;

import com.karan.authservice.Dto.FullUserDetails;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.exception.UserAlreadyExistsException;
import com.karan.authservice.exception.UserNotFoundException;
import com.karan.authservice.repository.UserRepository;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
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

    private static final Logger log = LoggerFactory.getLogger(UserDetailsIMPL.class);

    @Autowired
    public UserDetailsIMPL(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
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
            throw new UserNotFoundException("Cloud not found the user"+username);
        }
        return new FullUserDetails(user.get());
    }

}
