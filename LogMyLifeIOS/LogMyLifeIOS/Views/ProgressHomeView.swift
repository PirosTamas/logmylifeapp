import SwiftUI
import shared

struct ProgressHomeView: View {
    @StateObject private var state = ProgressObservable()

    private var todayDayName: String {
        let formatter = DateFormatter()
        formatter.locale = Locale(identifier: "en_US")
        formatter.dateFormat = "EEEE"
        return formatter.string(from: Date()).uppercased()
    }

    private var todaysAchievements: [AchievementProgress] {
        state.achievements.filter { a in
            a.scheduledDays.contains { $0.name == todayDayName }
        }
    }

    var body: some View {
        NavigationStack {
            List {
                Section("GOAL PROGRESS") {
                    if state.achievements.isEmpty {
                        Text("No goals yet. Add one to get started.")
                            .foregroundStyle(.secondary)
                    } else {
                        ForEach(state.achievements, id: \.id) { achievement in
                            AchievementRow(achievement: achievement)
                        }
                    }
                }

                Section("TODAY'S TASKS") {
                    if todaysAchievements.isEmpty {
                        Text("All caught up for today!")
                            .foregroundStyle(.secondary)
                    } else {
                        ForEach(todaysAchievements, id: \.id) { achievement in
                            AchievementRow(achievement: achievement)
                        }
                    }
                }
            }
            .navigationTitle("Home")
        }
    }
}

private struct AchievementRow: View {
    let achievement: AchievementProgress

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(achievement.name)
                .font(.headline)
            HStack {
                Text(achievement.category.name.capitalized)
                    .font(.caption)
                    .foregroundStyle(.secondary)
                Spacer()
                Text("\(achievement.currentSession)/\(achievement.numberOfSessions)")
                    .font(.caption)
                    .foregroundStyle(.secondary)
            }
            ProgressView(value: Double(achievement.currentSession),
                         total: Double(achievement.numberOfSessions))
                .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
        }
        .padding(.vertical, 4)
    }
}
