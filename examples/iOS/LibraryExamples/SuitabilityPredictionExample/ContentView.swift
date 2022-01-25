//
//  ContentView.swift
//  SuitabilityPredictionExample
//
//  Created by Matthew Avis on 2022-01-14.
//

import SwiftUI
import SuitabilityPredictionClient

struct ContentView: View {

    @State var suitabilityResult: SuitabilityResult?
//    @State var cancellable: AnyCancellable?

    let client: SuitabilityClient? = .configure(withKey: "")

    var body: some View {
        VStack {
            if let result = suitabilityResult {
                Text("CNS: \(result.cns)")
                Text("SNV: \(result.snv)")
                Text("Lesion: \(result.lesion)")
                Text("NEP: \(result.nep)")
            } else {
                Text("Running prediction")
            }
        }
        .onAppear {
            Task {
                suitabilityResult = try? await client?.predict(
                    input: UIImage(named: "hand")!)
            }
            /*
             cancellable = client?.predict(UIImage(named: "hand")!).sink(...)
             */
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
