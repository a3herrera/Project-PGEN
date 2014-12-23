package com.app.entity.history;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

import com.app.utils.ConstantsEntity;

public class ComprasPrvHistory implements DescriptorCustomizer {

	@Override
	public void customize(ClassDescriptor arg0) throws Exception {
		// TODO Auto-generated method stub
		HistoryPolicy policy = new HistoryPolicy();
		policy.addHistoryTableName(ConstantsEntity.compraHistoryName);
		policy.addStartFieldName(ConstantsEntity.StartDateName);
		policy.addEndFieldName(ConstantsEntity.EndDateName);
		arg0.setHistoryPolicy(policy);
	}

}
