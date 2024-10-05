    package com.example.dishdash.homepage.view;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import com.example.dishdash.NetworkFragment;
    import com.example.dishdash.R;
    import com.example.dishdash.db.FoodLocalDataSourceImp;
    import com.example.dishdash.favourite.view.FavFragment;
    import com.example.dishdash.homepage.presenter.HomePresenter;
    import com.example.dishdash.homepage.presenter.HomePresenterImpl;
    import com.example.dishdash.mealditalies.view.MealFragment;
    import com.example.dishdash.model.FoodRepositoryImpl;
    import com.example.dishdash.model.response.Category;
    import com.example.dishdash.model.response.Country;
    import com.example.dishdash.model.response.Food;
    import com.example.dishdash.network.FoodRempteDataSourceImpl;

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFragment extends Fragment implements HomeActivityInterface, OnClickListener {

        // Adapters
        private HomeAdapter homeAdapter;
        private CategoryAdapter categoryAdapter;
        private AllCategoriesAdapter allCategoriesAdapter;
        private CountriesAdapter countriesAdapter;
        private AllCountriesAdapter allCountriesAdapter;

        // Layout managers
        private LinearLayoutManager linearLayout, linearLayout2, layoutManager3, layoutManager4, layoutManager5;

        // Text views
        private TextView title, txtCountries, txtCategories;
        private RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
        private ProgressBar progressBar, progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;

        // Presenter
        HomePresenter homePresenter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            homePresenter = new HomePresenterImpl(this, FoodRepositoryImpl.getInstance(
                    FoodRempteDataSourceImpl.getInstance(),
                    FoodLocalDataSourceImp.getInstance(getContext())
            ));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            // Initialize UI
            initUI(view);

            // Check network
            if (!isNetworkConnected()) {
                navigateToNetworkFragment();
                return view;
            }
            // Set up RecyclerViews and adapters
            setupRecyclerViews();

            // Set the title of the fragment
            title = getActivity().findViewById(R.id.fragment_title);
            title.setText("Home Page");

            // Fetch data for the Home screen
            fetchData();

            return view;
        }

      /****************************************************************************/
      /************************** Initialize UI  **********************************/
      /****************************************************************************/
        private void initUI(View view) {
            recyclerView = view.findViewById(R.id.recView);
            recyclerView2 = view.findViewById(R.id.recView2);
            recyclerView3 = view.findViewById(R.id.recView3);
            recyclerView4 = view.findViewById(R.id.recView4);
            recyclerView5 = view.findViewById(R.id.recView5);

            progressBar = view.findViewById(R.id.progressBar);
            progressBar1 = view.findViewById(R.id.progressBar2);
            progressBar2 = view.findViewById(R.id.progressBar3);
            progressBar3 = view.findViewById(R.id.progressBar4);
            progressBar4 = view.findViewById(R.id.progressBar5);

            txtCountries = view.findViewById(R.id.txtCountries);
            txtCategories = view.findViewById(R.id.txtCategories);
        }

        /****************************************************************************/
        /********************** Set up RecyclerViews and adapters *******************/
        /****************************************************************************/
        private void setupRecyclerViews() {
            // Setup for (Random Meal)
            recyclerView.setHasFixedSize(true);
            linearLayout = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayout);
            homeAdapter = new HomeAdapter(getActivity(), new ArrayList<>());
            recyclerView.setAdapter(homeAdapter);

            // Setup for (All Categories)
            recyclerView2.setHasFixedSize(true);
            linearLayout2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView2.setLayoutManager(linearLayout2);
            categoryAdapter = new CategoryAdapter(getActivity(), new ArrayList<>(), this, txtCategories);
            recyclerView2.setAdapter(categoryAdapter);

            // Setup for (Meals in Category)
            recyclerView3.setHasFixedSize(true);
            layoutManager3 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView3.setLayoutManager(layoutManager3);
            allCategoriesAdapter = new AllCategoriesAdapter(getActivity(), new ArrayList<>(), this);
            recyclerView3.setAdapter(allCategoriesAdapter);

            // Setup for (All Countries)
            recyclerView4.setHasFixedSize(true);
            layoutManager4 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView4.setLayoutManager(layoutManager4);
            countriesAdapter = new CountriesAdapter(getActivity(), new ArrayList<>(), this, txtCountries);
            recyclerView4.setAdapter(countriesAdapter);

            // Setup for (Meals in Countries)
            recyclerView5.setHasFixedSize(true);
            layoutManager5 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            recyclerView5.setLayoutManager(layoutManager5);
            allCountriesAdapter = new AllCountriesAdapter(getActivity(), new ArrayList<>(), this);
            recyclerView5.setAdapter(allCountriesAdapter);
        }

       /****************************************************************************/
       /***************************** Fetch Data ***********************************/
       /****************************************************************************/
        private void fetchData() {
            homePresenter.getRandomFood();
            homePresenter.getCategoryFood();
            homePresenter.getCountries();
        }

        /****************************************************************************/
        /************************* Navigate to Network Fragment *********************/
        /****************************************************************************/
        private void navigateToNetworkFragment() {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NetworkFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        /****************************************************************************/
        /***************************** Check Network ********************************/
        /****************************************************************************/
        private boolean isNetworkConnected() {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        /****************************************************************************/
        /***************************** Show Data ************************************/
        /****************************************************************************/
        @Override
        public void showData(List<Food> food) {
            homeAdapter.setList(food);
            progressBar4.setVisibility(View.GONE);
            homeAdapter.notifyDataSetChanged();
        }

        @Override
        public void showErrMsg(String error) {
            navigateToNetworkFragment();
        }

        @Override
        public void showCategoryData(List<Category> meals) {
            categoryAdapter.setList(meals);
            progressBar3.setVisibility(View.GONE);
            categoryAdapter.notifyDataSetChanged();
        }

        @Override
        public void showCategoryErrMsg(String error) {
            navigateToNetworkFragment();
        }

        @Override
        public void showFood(List<Food> food) {
            MealFragment mealFragment = MealFragment.getInstance(food.get(0));
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mealFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        @Override
        public void showCountry(List<Country> food) {
            countriesAdapter.setList(food);
            progressBar2.setVisibility(View.GONE);
            countriesAdapter.notifyDataSetChanged();
        }

        @Override
        public void showFoodFromCountry(List<Food> food) {
            allCountriesAdapter.setList(food);
            progressBar.setVisibility(View.GONE);
            allCountriesAdapter.notifyDataSetChanged();
        }

        @Override
        public void showCategoryFood(List<Food> food) {
            allCategoriesAdapter.setList(food);
            progressBar1.setVisibility(View.GONE);
            allCategoriesAdapter.notifyDataSetChanged();
        }

        @Override
        public void showCategoryFoodErrMsg(String error) {
            navigateToNetworkFragment();
        }

        /****************************************************************************/
        /***************************** Click Listeners ******************************/
        /****************************************************************************/
        @Override
        public void onCategoryClick(String categoryId) {
            homePresenter.getFoodByCategory(categoryId);
        }

        @Override
        public void onCountryClick(String countryId) {
            homePresenter.getFoodByCountry(countryId);
        }

        @Override
        public void onFoodClick(String foodId) {
            homePresenter.getFoodById(foodId);
        }
    }
