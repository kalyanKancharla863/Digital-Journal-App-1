package com.digital_journal_app.backend.security;

import com.digital_journal_app.backend.model.Category;
import com.digital_journal_app.backend.model.DifficultyLevel;
import com.digital_journal_app.backend.repositories.CategoryRepository;
import com.digital_journal_app.backend.repositories.DifficultyLevelRepository;
import com.digital_journal_app.backend.security.jwt.AuthEntryPointJwt;
import com.digital_journal_app.backend.security.jwt.AuthTokenFilter;
import com.digital_journal_app.backend.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired

    private DifficultyLevelRepository difficultyLevelRepository;
    

    @Bean
    public AuthTokenFilter authenticaionJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig){
        return authconfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.csrf(csrf->csrf.disable())
                .cors(cors -> {})
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler) )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                        )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticaionJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);  // CRITICAL
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            
            if(!categoryRepository.existsByCategoryName("Personal")) {
                Category personalCategory = new Category();
                personalCategory.setCategoryName("Personal");
                categoryRepository.save(personalCategory);
            }
            if(!categoryRepository.existsByCategoryName("Work")) {
                Category workCategory = new Category();
                workCategory.setCategoryName("Work");
                categoryRepository.save(workCategory);
            }
            if(!categoryRepository.existsByCategoryName("Travel")) {
                Category travelCategory = new Category();
                travelCategory.setCategoryName("Travel");
                categoryRepository.save(travelCategory);
            }
            if(!categoryRepository.existsByCategoryName("Study")) {
                Category studyCategory = new Category();
                studyCategory.setCategoryName("Study");
                categoryRepository.save(studyCategory);
            }

            if(!difficultyLevelRepository.existsByDifficultyLevelName("Easy")) {
                DifficultyLevel easyLevel = new DifficultyLevel();
                easyLevel.setDifficultyLevelName("Easy");
                difficultyLevelRepository.save(easyLevel);
            }
            if(!difficultyLevelRepository.existsByDifficultyLevelName("Medium")) {
                DifficultyLevel mediumLevel = new DifficultyLevel();
                mediumLevel.setDifficultyLevelName("Medium");
                difficultyLevelRepository.save(mediumLevel);
            }
            if(!difficultyLevelRepository.existsByDifficultyLevelName("Hard")) {
                DifficultyLevel hardLevel = new DifficultyLevel();
                hardLevel.setDifficultyLevelName("Hard");
                difficultyLevelRepository.save(hardLevel);
            }
        };
    }

   }
