package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;

@Service(OverallPreferenceServiceImpl.BEAN_NAME)
public class OverallPreferenceServiceImpl extends BaseOhLohServiceImpl<Long, PreferenceEntity> implements OverallPreferenceService {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 610456503666069514L;
	
	public static final String BEAN_NAME =  "overallPreferenceService";
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public OverallPreferenceServiceImpl(@Qualifier("overallPreferenceRepository") OverallPreferenceRepository repository) {
		super(repository, PreferenceEntity.class);
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).countAllByProject(project);
	}

	@Override
	@Transactional(readOnly = true)
	public Double averagePreferenceByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).averagePreferenceByProject(project);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user) {
		return ((OverallPreferenceRepository) getRepository()).countAllByProjectAndUser(project, user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).findAllByProject(project);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user) {
		return ((OverallPreferenceRepository) getRepository()).findAllByProjectAndUser(project, user);
	}
	
	@Override
	@Transactional
	public PreferenceEntity save(PreferenceEntity entity) throws Exception {
		validateEntity(entity);
		return super.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	private void validateEntity(PreferenceEntity entity) {
		if (entity.getPreferenceReview() != null && entity.getPreferenceReview().getId() == null) {
			if (StringUtils.isEmpty(entity.getPreferenceReview().getDescription()) 
					&& StringUtils.isEmpty(entity.getPreferenceReview().getTitle())) {
				entity.setPreferenceReview(null);
			}
		}
		
		if (entity.getUser() != null && entity.getUser().getId() != null) {
			UserEntity user = getUserService().findById(entity.getUser().getId());
			
			entity.setUser(user);
			entity.setUserId(user.getId());
		}
	}
}
