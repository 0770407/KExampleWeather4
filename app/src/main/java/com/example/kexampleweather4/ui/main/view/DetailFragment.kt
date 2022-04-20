package com.example.kexampleweather4.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kexampleweather4.R
import com.example.kexampleweather4.databinding.DetailFragmentBinding
import com.example.kexampleweather4.ui.main.model.Weather
import com.example.kexampleweather4.ui.main.model.WeatherDTO
import com.example.kexampleweather4.ui.main.model.WeatherLoader


class DetailFragment : Fragment() {

    companion object {
        //константа для передачи данных из фрагмента во фрагмент
        const val WEATHER_EXTRA = "WEATHER_EXTRA"

        // фабричный метод
        fun newInstance(bundle: Bundle): DetailFragment =
            DetailFragment().apply { arguments = bundle }
//         fun newInstance(bundle: Bundle) : DetailFragment {
//            val fragment = DetailFragment()
//            fragment.arguments = bundle
//            return fragment
    }


    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 5. изменение для binding здесь:
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // т.к. weather передаем из фрагмента во фрагмент, то ее делает parcelable
        arguments?.getParcelable<Weather>(WEATHER_EXTRA)?.let { weather ->
            weather.city.also { city ->
                binding.cityName.text = city.name
                binding.lat.text = city.lat.toString()
                binding.lon.text = city.lon.toString()
            }

            WeatherLoader(
                weather.city.lat,
                weather.city.lon,
                object : WeatherLoader.WeatherLoaderListener {
                    override fun onLoaded(weatherDTO: WeatherDTO) {
                        requireActivity().runOnUiThread {
                            displayWeather(weatherDTO)
                        }
                    }
                    override fun onFailed(throwable: Throwable) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                throwable.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }).goToInternet()
        }
    }

    private fun displayWeather(weather: WeatherDTO) {
        with(binding) {
            temperatureValue.text = weather.fact?.temp.toString()
            feelsLikeValue.text = weather.fact?.feels_like.toString()
            weatherCondition.text = weather.fact?.condition.toString()
        }

    }


}