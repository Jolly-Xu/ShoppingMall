package com.xujialin.SafetyVerification;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xujialin.Utils.UUIDGenerator;
import com.xujialin.entity.Userinfo;
import com.xujialin.service.UserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author XuJiaLin
 * @date 2021/8/3 20:50
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private PasswordEncoder Encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断username是否为空
        if (username.isBlank())
            throw new UsernameNotFoundException("用户名为空");
        MyUserDetails userDetails = new MyUserDetails();

        String key = UUIDGenerator.GeneratorUserNameInfoKey(username);
        Userinfo o = null;
        //先查询Redis数据库
        o = (Userinfo) redisTemplate.opsForValue().get(key);

        //如果redis没有数据
        if (o == null) {
            QueryWrapper wrapper = new QueryWrapper();
            Map<String, Object> map = new HashMap<>();
            map.put("user_name", username);
            map.put("is_logic_delete", 0);
            wrapper.allEq(map);
            o = userinfoService.getOne(wrapper);
            if (o == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            redisTemplate.opsForValue().set(key, o);
            redisTemplate.expire(key,60,TimeUnit.SECONDS);
        }
        userDetails.setUser(o);
        userDetails.setUsername(o.getUserName());
        userDetails.setPassword(Encoder.encode(o.getPassword()));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_User");
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        userDetails.setAuthorities(authorities);
        return userDetails;
    }
}
