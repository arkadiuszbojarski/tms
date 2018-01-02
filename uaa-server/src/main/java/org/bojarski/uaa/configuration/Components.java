/**
 * 
 */
package org.bojarski.uaa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Common security Beans.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Configuration
public class Components {

	@Value("${store}")
	private String store;
	
	@Value("${alias}")
	private String alias;
	
	@Value("${store_key}")
	private String store_key;

	/**
	 * Spring Bean returning BCrypt password encoder for encoding user password.
	 * 
	 * @return {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Spring Bean returning JSON Web Token Converter for creating and signing access tokens.
	 * 
	 * @return @{link {@link JwtAccessTokenConverter}
	 */
	@Bean
	public JwtAccessTokenConverter converter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(store), store_key.toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
		return converter;
	}

	/**
	 * Spring Bean returning JSON Web Token Store.
	 * 
	 * @return @{link {@link JwtTokenStore}
	 */
	@Bean
	public TokenStore store() {
		return new JwtTokenStore(converter());
	}

	/**
	 * Spring Bean returing configured Token Store User Approval Handler.
	 * 
	 * @param tokenStore a {@link TokenStore} implementation used in application.
	 * @param clientDetailsService a {@link ClientDetailsService} implementation used in application.
	 *  
	 * @return {@link TokenStoreUserApprovalHandler}
	 */
	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore, ClientDetailsService clientDetailsService) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	/**
	 * Spring Bean returning configured Aproval Store.
	 * 
	 * @param tokenStore a {@link TokenStore} implementation used in application.
	 * 
	 * @return {@link ApprovalStore}
	 */
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
}
