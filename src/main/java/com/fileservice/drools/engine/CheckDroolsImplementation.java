package com.fileservice.drools.engine;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.fileservice.beans.ProductFamilyDetails;

/**
 * @author vivek
 *
 */
public class CheckDroolsImplementation {

	public static final void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rule");

		ProductFamilyDetails product = new ProductFamilyDetails();
		product.setType("ILL_GVPN");
		product.setBandWidth(50);

		kSession.insert(product);
		kSession.fireAllRules();

		System.out.println("The Product Family Name " + product.getFamilyName() + " The type of family is"
				+ product.getType() + " Bandwidth " + product.getBandWidth());

	}

}
