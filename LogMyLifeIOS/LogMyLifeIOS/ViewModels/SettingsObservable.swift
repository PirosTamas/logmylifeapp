import Foundation
import Combine
import shared

@MainActor
final class SettingsObservable: ObservableObject {
    @Published var name: String = ""
    @Published var weightUnit: String = "kg"
    @Published var waterReminderEnabled: Bool = true
    @Published var isDarkMode: Bool = false

    private let vm: SettingsViewModel
    private var tasks: [Task<Void, Never>] = []

    init() {
        self.vm = getSettingsViewModel()
        
        tasks.append(Task { [weak self] in 
            guard let self = self else { return }
            for await v in self.vm.name { 
                await MainActor.run { self.name = v } 
            } 
        })
        tasks.append(Task { [weak self] in 
            guard let self = self else { return }
            for await v in self.vm.weightUnit { 
                await MainActor.run { self.weightUnit = v } 
            } 
        })
        tasks.append(Task { [weak self] in 
            guard let self = self else { return }
            for await v in self.vm.waterReminderEnabled { 
                await MainActor.run { self.waterReminderEnabled = v.boolValue } 
            } 
        })
        tasks.append(Task { [weak self] in 
            guard let self = self else { return }
            for await v in self.vm.isDarkMode { 
                await MainActor.run { self.isDarkMode = v.boolValue } 
            } 
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }

    func setName(_ name: String) {
        vm.setName(name: name)
    }

    func setWeightUnit(_ unit: String) {
        vm.setWeightUnit(unit: unit)
    }

    func setWaterReminderEnabled(_ enabled: Bool) {
        vm.setWaterReminderEnabled(enabled: enabled)
    }

    func setDarkMode(_ enabled: Bool) {
        vm.setDarkMode(enabled: enabled)
    }
}
