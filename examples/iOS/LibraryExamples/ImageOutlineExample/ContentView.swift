//
//  ContentView.swift
//  ImageOutlineExample
//
//  Created by Matthew Avis on 2022-01-14.
//

import SwiftUI
import ImageOutline

let outlineClient = ImageOutlineClient.configure(withKey: "")

struct ContentView: View {
    var body: some View {
        Image(
            uiImage: outlineClient
                .outlineImage(
                    UIImage(named: "hand")!,
                        .white
                )!
        )
            .resizable()
            .aspectRatio(contentMode: .fit)
            .background(Color.black)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
