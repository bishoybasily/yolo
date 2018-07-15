package com.gmail.bishoybasily.yolo.sample

import com.gmail.bishoybasily.yolo.annotations.EnableGraph
import com.gmail.bishoybasily.yolo.generated.Graph

@EnableGraph(lazyBeans = [User::class, Database::class])
class Main

fun main(args: Array<String>) {

    Graph.getInstance().user(User.bishoy())
    Graph.getInstance().database(Oracle(User.bishoy()))

    val screen = Screen()

    println(
            screen
                    .activityOne
                    .serviceRegistration
                    .repositoryUsers
                    .getDatabase()
                    .type()
    )

}
