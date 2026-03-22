import Foundation
import shared

@MainActor
final class YearInPixelsObservable: ObservableObject {
    @Published var moodAnswers: [DailyLifeDataAnswer] = []

    private let vm = YearInPixelsViewModel()
    private var tasks: [Task<Void, Never>] = []

    init() {
        tasks.append(Task { [weak self] in
            try? await Task.sleep(nanoseconds: 100_000_000)
            for await answers in vm.moodAnswers {
                await MainActor.run { self?.moodAnswers = answers }
            }
        })
    }

    deinit {
        tasks.forEach { $0.cancel() }
    }
}
