package com.example.kexampleweather4.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kexampleweather4.R
import com.example.kexampleweather4.databinding.MainFragmentBinding
import com.example.kexampleweather4.ui.main.viewmodel.AppState
import com.example.kexampleweather4.ui.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    // *** данная инициализация заменена на lazy
    //    private lateinit var adapter: MainAdapter
    private val adapter: MainAdapter by lazy { MainAdapter () }


    // Тут BINDING for fragments (для активити все проще)
    // 1. Сделать запись в gradle
    // 2. Создаем вспомогательный объект
    private var _binding: MainFragmentBinding? = null

    // 3. Создаем объект
    private val binding get() = _binding!!

    // 4. занулить вспомогательный binding здесь
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // 5. см. ниже добавить строки в onCreateView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 5. изменение для binding здесь:
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // *** данная инициализация перенена наверх в lazy
        // сама инициализация произойдет когда будет вызван лисенер adapter.listener
        // adapter = MainAdapter()

//      Внутри адаптера есть строка (var listener: OnItemViewClickListener? = null), создающая
//      лисенер по своему интерфейсу (интерфейс прописан в MainAdapter, но может быть и отдельно).
//      Как вариант этот лисенер можно передать как интерфейс при создании адаптера в качестве параметра
//      (object: MainAdapter.OnItemViewClickListener {переопределение метода ...}
//
//      Примечание:     adapter.listener = object: MainAdapter.OnItemViewClickListener {
//                      override fun onItemClick(weather: Weather) {
//                      !!!!!!! т.к. интерфейс OnItemViewClickListener в MainAdapter-е сделали функциональным
//                      (fun interface), то эти строчки преобразовали в лямбду:
        adapter.listener = MainAdapter.OnItemViewClickListener { weather ->
            //здесь OnItemViewClickListener переопределен для целей загрузки другого фрагмента
            activity?.supportFragmentManager?.let {
                // создаем bundle, кладем туда погоду и передаем во фрагмент, кот.в контейнере
                it.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance(Bundle().apply {
                        putParcelable(DetailFragment.WEATHER_EXTRA, weather)
                    } ))
                    .addToBackStack("")
                    .commit()
                //.replace - перерисовывает фрагмент, .add - добавляет текст поверх

//                классический вариант
//                val bundle = Bundle()
//                bundle.putParcelable(DetailFragment.WEATHER_EXTRA, weather)
//                it.beginTransaction()
//                    .replace(R.id.container, DetailFragment.newInstance(bundle))
//                    .addToBackStack("")
//                    .commit()
            } ?: throw Exception () //это не нужно, просто чтобы let оставить для показа
        }

        // без биндинга все работает - на id 'kotlin-android-extensions'
        recycleView.adapter = adapter
        // recycleView.adapter = myAdapter ---это recycleView.setAdapter(myAdapter)
        // recycleView.adapter             ---это recycleView.getAdapter

        mainFragmentFAB.setOnClickListener {
            viewModel.onLanguageChange()
        }


        // <<<<<<<<<<<<<<<<<< 2 and 4.  И преобразовать в лямбду это
        // viewModel.getData().observe(viewLifecycleOwner, object : Observer<String>{
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        // <<<<<<<<<<<<<<<<<< 2 and 4.
        viewModel.liveDataIsRus.observe(viewLifecycleOwner) { isRus ->
            if (isRus) {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            // <<<<<<<<<<<<<<<<<< 3.0 - дергаем liveData
            viewModel.getWeatherFromLocalSource()
        }
    }

    private fun renderData(state: AppState) {

        when (state) {
            // is - это аналогия с java - typeOf, т.е. тип объекта
            is AppState.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE  ____меняем на:
                binding.loadingLayout.myMethodShow()
            }
            is AppState.Success -> {
                binding.loadingLayout.myMethodHide()
                adapter.weatherData = state.weather
//                binding.message.text = "${state.weather.city.name}" +
//                        "\n lat/lon ${state.weather.city.lat}" + " ${state.weather.city.lon}" +
//                        "\n temperature ${state.weather.temperature}" +
//                        "\n feelsLike ${state.weather.feelsLike}"
            }
            is AppState.Error -> {
                binding.loadingLayout.myMethodHide()
                binding.mainFragmentFAB.myShowSnackBar(
                    "Error: ${state.error}",
                    "Reload",
                    { viewModel.getWeatherFromLocalSource()}
                )
//                Snackbar.make(mainFragmentFAB,"Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
//                    .show()
            }
        }

    }

}