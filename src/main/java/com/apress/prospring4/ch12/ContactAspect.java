package com.apress.prospring4.ch12;

import java.util.Arrays;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.engine.spi.SelfDirtinessTracker;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ContactAspect {

    @Pointcut("execution(* com.apress.prospring4.ch12.ContactServiceImpl.save(..))")
    public void inMyDependency() {
    }

	@Before("inMyDependency() && target(bean) && args(contact)")
	public void doBeforeSave(Object bean, Object contact) {
		Contact c = (Contact)contact;
		System.out.println("YakimKY --- before - bean = " + bean);
		System.out.println("YakimKY --- before - contact = " + c);
		
		if (c instanceof SelfDirtinessTracker) {
			System.out.println("YakimKY --- before - contace instance of SelfDirtinessTracker");
			if (((SelfDirtinessTracker)c).$$_hibernate_hasDirtyAttributes()) {
				System.out.println("YakimKY --- this contact has dirty");
				String[] strDirty = ((SelfDirtinessTracker)c).$$_hibernate_getDirtyAttributes();
				System.out.println("YakimKY --- dirty atr = " + Arrays.asList(strDirty));
			}
			else {
				System.out.println("YakimKY --- contact not dirty");
			}
		} else {
			System.out.println("YakimKY --- contact is not instance of SelfDirtinessTracker");
		}
		
		System.out.println("YakimKY --- before - this method is aop methods - before save");
	}

	@After("inMyDependency()")
	public void doAfterSave() {
		System.out.println("YakimKY --- after - this method is aop methods - after save");
	}
}
