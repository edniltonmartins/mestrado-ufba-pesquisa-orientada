
package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRepository;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.impl.BaseServiceImpl;

@Service(UserServiceImpl.BEAN_NAME)
public class UserServiceImpl extends BaseServiceImpl<Long, UserEntity>
		implements UserService {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4249165761507596580L;
	
	public static final String BEAN_NAME =  "userService";

	@Autowired
	public UserServiceImpl(@Qualifier("userRepository") UserRepository repository) {
		super(repository,  UserEntity.class);
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserEntity findByLogin(String login) {
		return ((UserRepository)  getRepository()).findByLogin(login);
	}
	
	@Transactional(readOnly = true)
	public UserEntity findByEmail(String email) {
		return ((UserRepository)  getRepository()).findByEmail(email);
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserEntity findBySocialLogin(String socialUsername) {
		return ((UserRepository)  getRepository()).findBySocialLogin(socialUsername);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserEntity save(UserEntity entity) throws Exception {
		validateEntity(entity);
		return super.save(entity);
	}

	private void validateEntity(UserEntity entity) {
		if (entity != null && entity.getId() == null) {
			entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			entity.setEnabled(Boolean.TRUE);
			entity.setLocked(Boolean.FALSE);
		}
		
		if (StringUtils.isEmpty(entity.getName())) {
			String name = entity.getFirstName() + " " + entity.getMiddleName() + " " + entity.getLastName();
			name = name.replaceAll("  ", " ").replaceAll("null", "");
			entity.setName(name);
		}
		
	}
}

