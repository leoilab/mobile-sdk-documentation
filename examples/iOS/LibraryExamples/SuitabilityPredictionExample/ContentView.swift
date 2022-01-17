//
//  ContentView.swift
//  SuitabilityPredictionExample
//
//  Created by Matthew Avis on 2022-01-14.
//

import SwiftUI
import SuitabilityPredictionClient

let client: SuitabilityClient = .suitability(apiKey: "")

struct ContentView: View {

    @State var suitabilityResult: SuitabilityResult?

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
                suitabilityResult = try? await client.predict(
                    input: UIImage(named: "hand")!)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}