import Foundation
import Combine
import shared

@MainActor
final class YearInPixelsObservable: ObservableObject {
    @Published var moodAnswers: [DailyLifeDataAnswer] = []

    private let vm: YearInPixelsViewModel
    private var tasks: [Task<Void, Never>] = []

    init() {
        // Use helper function to get ViewModel instance
        self.vm = getYearInPixelsViewModel()
        
        tasks.append(Task { [weak self] in
            guard let self = self else { return }
            try? await Task.sleep(nanoseconds: 100_000_000)
            for await answers in self.vm.moodAnswers {
                await MainActor.run { self.moodAnswers = answers }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }
}
