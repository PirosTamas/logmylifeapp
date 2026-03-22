import SwiftUI
import shared

struct YearInPixelsView: View {
    @StateObject private var state = YearInPixelsObservable()

    private let columns = Array(repeating: GridItem(.flexible(), spacing: 4), count: 12)
    private let monthNames = ["J","F","M","A","M","J","J","A","S","O","N","D"]
    private let currentYear: Int = Calendar.current.component(.year, from: Date())

    private var answersByDate: [String: Int] {
        var dict: [String: Int] = [:]
        for answer in state.moodAnswers {
            let key = "\(answer.createdAt.year)-\(answer.createdAt.monthNumber)-\(answer.createdAt.dayOfMonth)"
            dict[key] = Int(answer.answer) ?? 0
        }
        return dict
    }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 8) {
                    HStack(spacing: 4) {
                        ForEach(0..<12, id: \.self) { month in
                            Text(monthNames[month])
                                .font(.system(size: 9, weight: .medium))
                                .frame(maxWidth: .infinity)
                        }
                    }
                    .padding(.horizontal, 8)

                    LazyVGrid(columns: columns, spacing: 4) {
                        ForEach(1...31, id: \.self) { day in
                            ForEach(1...12, id: \.self) { month in
                                let mood = answersByDate["\(currentYear)-\(month)-\(day)"]
                                RoundedRectangle(cornerRadius: 2)
                                    .fill(moodColor(mood))
                                    .aspectRatio(1, contentMode: .fit)
                            }
                        }
                    }
                    .padding(.horizontal, 8)
                }
                .padding(.top, 8)
            }
            .navigationTitle("Year in Pixels")
        }
    }

    private func moodColor(_ mood: Int?) -> Color {
        guard let mood else { return Color(.systemGray5) }
        switch mood {
        case 5: return Color(red: 0.075, green: 0.925, blue: 0.357)
        case 4: return Color.green.opacity(0.6)
        case 3: return Color.yellow.opacity(0.7)
        case 2: return Color.orange.opacity(0.7)
        case 1: return Color.red.opacity(0.7)
        default: return Color(.systemGray5)
        }
    }
}
