import Foundation
import shared

@MainActor
final class ProgressObservable: ObservableObject {
    @Published var achievements: [AchievementProgress] = []

    private let vm = ProgressViewModel()
    private var tasks: [Task<Void, Never>] = []

    init() {
        tasks.append(Task { [weak self] in
            for await list in vm.getAllAchievementProgresses {
                await MainActor.run { self?.achievements = list }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }
}
