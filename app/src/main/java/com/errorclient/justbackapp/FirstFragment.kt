package com.errorclient.justbackapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.errorclient.justbackapp.databinding.FragmentFirstBinding

const val KEY_STRING = "key_string"

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var airplaneReceiver: AirplaneModeReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)

        /**
         * Если в savedStateHandle есть запись с key = KEY_STRING,
         * то записываем значение в firstText.text
         */
        binding.firstText.text =
            findNavController()
                .currentBackStackEntry
                ?.savedStateHandle
                ?.get<String>(KEY_STRING) ?: "Arg"

        return binding.root
    }

    /**
     * Подписываемся, когда пользователь может взаимодействовать с фрагментом
     */
    override fun onStart() {
        super.onStart()

        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        airplaneReceiver = AirplaneModeReceiver()
        requireActivity().registerReceiver(airplaneReceiver, filter)

        binding.buttonToSecondFragment.setOnClickListener {

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

    /**
     * Отписываемся, когда пользователь перестает взаимодействовать с фрагментом
     */
    override fun onStop() {

        requireActivity().unregisterReceiver(airplaneReceiver)
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Если событие произошло, то запускаем ifAirplaneModeChange
     */
    inner class AirplaneModeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            ifAirplaneModeChange(context)
        }
    }

    /**
     * Ф-я проверяет включен ли AIRPLANE_MODE
     * Если да - показываем взлетающий самолет
     * Иначе - приземляющийся
     */
    private fun ifAirplaneModeChange(context: Context?) {
        val isAirplaneModeOn = Settings.Global.getInt(
            context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
        )

        if (isAirplaneModeOn != 0) {
            binding.batteryText.text = getString(R.string.airplane_on)
        } else {
            binding.batteryText.text = getString(R.string.airplane_off)
        }
    }
}