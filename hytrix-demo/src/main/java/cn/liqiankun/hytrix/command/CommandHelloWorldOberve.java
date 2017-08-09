/**
 * 
 */
package cn.liqiankun.hytrix.command;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * @author liqiankun
 *
 */
public class CommandHelloWorldOberve extends HystrixObservableCommand<String> {

	private final String name;
	
	protected CommandHelloWorldOberve(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> observer) {
				try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here
                        observer.onNext("Hello");
                        observer.onNext(name + "!");
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
				
			}
			
		});
	}

}
