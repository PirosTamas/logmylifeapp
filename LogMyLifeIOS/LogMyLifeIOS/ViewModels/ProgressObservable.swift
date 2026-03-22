import Foundation
import Combine
import shared

@MainActor
final class ProgressObservable: ObservableObject {
    @Published var achievements: [AchievementProgress] = []

    private let vm: ProgressViewModel
    private var tasks: [Task<Void, Never>] = []

    init() {
        self.vm = getProgressViewModel()
        
        tasks.append(Task { [weak self] in
            guard let self = self else { return }
            for await list in self.vm.getAllAchievementProgresses {
                await MainActor.run { self.achievements = list }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }
}
