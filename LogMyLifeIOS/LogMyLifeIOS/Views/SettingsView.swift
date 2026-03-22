import SwiftUI

struct SettingsView: View {
    @StateObject private var state = SettingsObservable()
    @State private var showNameAlert = false
    @State private var nameInput = ""

    var body: some View {
        NavigationStack {
            List {
                Section("PROFILE") {
                    HStack {
                        Text("Name")
                        Spacer()
                        Text(state.name.isEmpty ? "Not set" : state.name)
                            .foregroundStyle(.secondary)
                        Button {
                            nameInput = state.name
                            showNameAlert = true
                        } label: {
                            Image(systemName: "pencil.circle.fill")
                                .foregroundStyle(Color(red: 0.075, green: 0.925, blue: 0.357))
                        }
                    }
                }

                Section("WORKOUT") {
                    Picker("Weight unit", selection: $state.weightUnit) {
                        Text("kg").tag("kg")
                        Text("lbs").tag("lbs")
                    }
                    .pickerStyle(.segmented)
                    .onChange(of: state.weightUnit) { _, newValue in
                        state.setWeightUnit(newValue)
                    }
                }

                Section("APPEARANCE") {
                    Toggle("Dark mode", isOn: $state.isDarkMode)
                        .onChange(of: state.isDarkMode) { _, newValue in
                            state.setDarkMode(newValue)
                        }
                        .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
                }

                Section("NOTIFICATIONS") {
                    Toggle("Water reminder", isOn: $state.waterReminderEnabled)
                        .onChange(of: state.waterReminderEnabled) { _, newValue in
                            state.setWaterReminderEnabled(newValue)
                        }
                        .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
                }
            }
            .navigationTitle(state.name.isEmpty ? "Settings" : state.name)
        }
        .alert("Edit name", isPresented: $showNameAlert) {
            TextField("Your name", text: $nameInput)
            Button("Save") { state.setName(nameInput) }
            Button("Cancel", role: .cancel) {}
        }
    }
}
