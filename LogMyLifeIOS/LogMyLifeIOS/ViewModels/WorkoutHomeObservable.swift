import Foundation
import shared

@MainActor
final class WorkoutHomeObservable: ObservableObject {
    @Published var workoutPlans: [WorkoutPlan] = []

    private let vm = WorkoutHomeViewModel()
    private var tasks: [Task<Void, Never>] = []

    init() {
        tasks.append(Task { [weak self] in
            for await plans in vm.workoutPlansForToday {
                await MainActor.run { self?.workoutPlans = plans }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }

    func startWorkout(planId: Int32) async -> Int64? {
        do {
            return try await vm.addWorkoutSession(planId: planId)
        } catch {
            print("Failed to start workout: \(error)")
            return nil
        }
    }
}
