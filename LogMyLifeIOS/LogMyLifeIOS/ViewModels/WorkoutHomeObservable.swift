import Foundation
import Combine
import shared

@MainActor
final class WorkoutHomeObservable: ObservableObject {
    @Published var workoutPlans: [WorkoutPlan] = []

    private let vm: WorkoutHomeViewModel
    private var tasks: [Task<Void, Never>] = []

    init() {
        self.vm = getWorkoutHomeViewModel()
        
        tasks.append(Task { [weak self] in
            guard let self = self else { return }
            for await plans in self.vm.workoutPlansForToday {
                await MainActor.run { self.workoutPlans = plans }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }

    func startWorkout(planId: Int32) async -> Int64? {
        do {
            let result = try await vm.addWorkoutSession(planId: planId)
            return result.int64Value
        } catch {
            print("Failed to start workout: \(error)")
            return nil
        }
    }
}
