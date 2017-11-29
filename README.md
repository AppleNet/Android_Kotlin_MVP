# Android_Kotlin_MVP

# Material Design
<!--
        android:background="?android:attr/selectableItemBackground"波纹有边界
        android:background="?android:attr/selectableItemBackgroundBorderless" 波纹超出边界
-->


# LiveData
    LiveData 是一个 observable 数据的持有类，和普通的 observable 不同，LiveData 是生命周期感知的，意味着它代表其它应用组件譬如 Activity、Fragment、Service 的生命周期。
    这确保了 LiveData 只会更新处于活跃状态的组件。

    LiveData 通过内部类的形式实现了 LifecycleObserver，它整个工作流程大概是这样的：
        1.将实现了 LifecycleObserver 的内部类注册到 owner 的 Lifecycle。
        2.LifecycleObserver 监听了 Lifecycle 所有的生命周期事件
        3.当有生命周期事件发生时，检查 Lifecycle 的状态是否至少是 STARTED 来判断 lifecycle 是否处于活跃状态
        4.当维护的值被改变时，如果 Lifecycle 处于活跃状态，通知观察者(实现了 android.arch.lifecycle.Observer 接口的对象)，否则什么也不做
        5.当 Lifecycle 从非活跃状态恢复到活跃状态时，检查维护的值是否在非活跃期间有更新过，如果有，通知观察者
        6.当 Lifecycle 处于完结状态 DESTROYED 时，从 Lifecycle 中移除 LifecycleObserver

    LiveData的优势
        1.没有内存泄漏：因为 Observer 被绑定到它们自己的 Lifecycle 对象上，所以，当它们的 Lifecycle 被销毁时，它们能自动的被清理
        2.不会因为 activity 停止而崩溃：如果 Observer 的 Lifecycle 处于闲置状态（例如：activity 在后台时），它们不会收到变更事件
        3.始终保持数据最新：如果 Lifecycle 重新启动（例如：activity 从后台返回到启动状态）将会收到最新的位置数据（除非还没有）
        4.正确处理配置更改：如果 activity 或 fragment 由于配置更改（如：设备旋转）重新创建，将会立即收到最新的有效位置数据
        5.资源共享：可以只保留一个 MyLocationListener 实例，只连接系统服务一次，并且能够正确的支持应用程序中的所有观察者
        6.不再手动管理生命周期你可能已经注意到，fragment 只是在需要的时候观察数据，不用担心被停止或者在停止之后启动观察。由于 fragment 在观察数据时提供了其 Lifecycle，所以 LiveData 会自动管理这一切

Use LiveData

    1.Create LiveData Objects
      LiveData是一个泛型包装类，包含实现Collections接口的对象，例如List， ViewModel通常持有LiveData对象并且提供一个get方法获取LiveData, 通常LiveData不需要set
      
      public class NameViewModel extends ViewModel {
      // Create a LiveData with a String
      private MutableLiveData<String> mCurrentName;
          public MutableLiveData<String> getCurrentName() {
              if (mCurrentName == null) {
                  mCurrentName = new MutableLiveData<String>();
              }
              return mCurrentName;
          }
        // Rest of the ViewModel...
      }
      
    2.Observe LiveData objects
      大多数情况下，一个app组件的onCreate方法，是开始observing LiveData的地方
       1) 确保系统没有从Activity或者Fragment的onResume方法的多余调用
       2) 确保当Activity或者Fragment变成活跃状态的时候 能尽快展示数据，只要app的组件进入STARTED状态，从LiveData对象中接收观察到的最新的值
      通常，当数据发生变化的时候，LiveData发送更新通知给活跃的观察者。唯一的异常行为是当观察者收到更新的时候，它的状态从inactive状态变成了active状态。并且，如果这个观察者再一次从inactive变成active，它仅仅
      接收最后一次变成active的数据
      
      public class NameActivity extends AppCompatActivity {
          private NameViewModel mModel;
          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              // Other code to setup the activity...
              // Get the ViewModel.
              mModel = ViewModelProviders.of(this).get(NameViewModel.class);
              // Create the observer which updates the UI.
              final Observer<String> nameObserver = new Observer<String>() {
                  @Override
                  public void onChanged(@Nullable final String newName) {
                      // Update the UI, in this case, a TextView.
                      mNameTextView.setText(newName);
                  }
              };
              // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
              mModel.getCurrentName().observe(this, nameObserver);
          }
      }
      
    3.Update LiveData objects
      LiveData并没有公共的可以更新存贮的数据的方法，MutableLivedData暴露setValue和postValue方法
      mButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              String anotherName = "John Doe";
              mModel.getCurrentName().setValue(anotherName);
          }
      });

Extend LiveData
    
    1.LiveData认为，一个Observer的生命周期状态是STARTED或者RESUMED的时候，就是一个active状态。举例说明怎样继承一个LiveData类
      public class StockLiveData extends LiveData<BigDecimal> {
          private StockManager mStockManager;
      
          private SimplePriceListener mListener = new SimplePriceListener() {
              @Override
              public void onPriceChanged(BigDecimal price) {
                  setValue(price);
              }
          };
      
          public StockLiveData(String symbol) {
              mStockManager = new StockManager(symbol);
          }
      
          @Override
          protected void onActive() {
              mStockManager.requestPriceUpdates(mListener);
          }
      
          @Override
          protected void onInactive() {
              mStockManager.removeUpdates(mListener);
          }
      }
      
      onActive() 当LiveData持有一个active observer的时候 这个方法被调用
      onInactive() 当LiveData不持有任何active observer的时候 这个方法被调用。
      setValue() 更新数据的时候 调用
      
      举例说明怎样使用StockLiveData
      public class MyFragment extends Fragment {
          @Override
          public void onActivityCreated(Bundle savedInstanceState) {
              super.onActivityCreated(savedInstanceState);
              LiveData<BigDecimal> myPriceListener = ...;
              myPriceListener.observe(this, price -> {
                  // Update the UI.
              });
          }
      }
      
      请注意，addObserver() 方法将 LifecycleOwner 作为第一个参数传递。这样做表示该观察者应该绑定到 Lifecycle，意思是：
      如果 Lifecycle 不处于活动状态（STARTED 或 RESUMED），即使该值发生变化也不会调用观察者。
      如果 Lifecycle 被销毁，那么自动移除观察者。
    
    LiveData 是生命周期感知的事实给我们提供了一个新的可能：可以在多个 activity，fragment 等之间共享它。为了保持实例简单，可以将其作为单例
      public class StockLiveData extends LiveData<BigDecimal> {
          private static StockLiveData sInstance;
          private StockManager mStockManager;
      
          private SimplePriceListener mListener = new SimplePriceListener() {
              @Override
              public void onPriceChanged(BigDecimal price) {
                  setValue(price);
              }
          };
      
          @MainThread
          public static StockLiveData get(String symbol) {
              if (sInstance == null) {
                  sInstance = new StockLiveData(symbol);
              }
              return sInstance;
          }
      
          private StockLiveData(String symbol) {
              mStockManager = new StockManager(symbol);
          }
      
          @Override
          protected void onActive() {
              mStockManager.requestPriceUpdates(mListener);
          }
      
          @Override
          protected void onInactive() {
              mStockManager.removeUpdates(mListener);
          }
      }
      
      调用方式：
      public class MyFragment extends Fragment {
          @Override
          public void onActivityCreated(Bundle savedInstanceState) {
              StockLiveData.get(getActivity()).observe(this, price -> {
                  // Update the UI.
              });
          }
      }


