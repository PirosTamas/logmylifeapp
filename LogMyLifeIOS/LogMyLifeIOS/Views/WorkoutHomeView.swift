import SwiftUI
import shared

struct WorkoutHomeView: View {
    @StateObject private var state = WorkoutHomeObservable()
    @State private var startedSessionId: Int64? = nil
    @State private var showStarted = false

    var body: some View {
        NavigationStack {
            Group {
                if state.workoutPlans.isEmpty {
                    ContentUnavailableView(
                        "No workouts today",
                        systemImage: "figure.strengthtraining.traditional",
                        description: Text("No workout plans scheduled for today.")
                    )
                } else {
                    List(state.workoutPlans, id: \.id) { plan in
                        WorkoutPlanRow(plan: plan) {
                            Task {
                                startedSessionId = await state.startWorkout(planId: plan.id)
                                showStarted = true
                            }
                        }
                    }
                }
            }
            .navigationTitle("Workout")
            .alert("Workout started!", isPresented: $showStarted) {
                Button("OK") {}
            } message: {
                if let id = startedSessionId {
                    Text("Session #\(id) created.")
                }
            }
        }
    }
}

private struct WorkoutPlanRow: View {
    let plan: WorkoutPlan
    let onStart: () -> Void

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(plan.name)
                    .font(.headline)
                Text("Session \(plan.currentSession)/\(plan.numberOfSessions)")
                    .font(.caption)
                    .foregroundStyle(.secondary)
            }
            Spacer()
            Button("Start", action: onStart)
                .buttonStyle(.borderedProminent)
                .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
        }
        .padding(.vertical, 4)
    }
}
