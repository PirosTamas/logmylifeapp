import SwiftUI

struct ContentView: View {
    var body: some View {
        TabView {
            ProgressHomeView()
                .tabItem {
                    Label("Home", systemImage: "chart.bar.fill")
                }

            WorkoutHomeView()
                .tabItem {
                    Label("Workout", systemImage: "figure.strengthtraining.traditional")
                }

            YearInPixelsView()
                .tabItem {
                    Label("Year", systemImage: "calendar")
                }

            SettingsView()
                .tabItem {
                    Label("Settings", systemImage: "gearshape.fill")
                }
        }
        .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
    }
}
