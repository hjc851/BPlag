package bplag

import jcinterpret.testconsole.pipeline.comparison.ProcessedProjectComparator
import jcinterpret.testconsole.utils.ProjectModelBuilder
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object GraphComparator {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 1) {
            error("Expected 1 argument: bplag.GraphComparator <input-path-to-graphs>")
        }

        val root = Files.list(Paths.get(args[0]))
            .filter { Files.isDirectory(it) && !Files.isHidden(it) }
            .use { it.toList() }
            .sorted()

        for (lproj in root) {
            val lmodel = ProjectModelBuilder.build(lproj)

            for (rproj in root) {

                if (lproj == rproj) continue

                {
                    val rmodel = ProjectModelBuilder.build(rproj)
                    val sim = ProcessedProjectComparator.compareExecutionGraphs(lmodel, rmodel)

                    println("${lmodel.projectId}\t${rmodel.projectId}\t${sim.lsim}")
                    println("${rmodel.projectId}\t${lmodel.projectId}\t${sim.rsim}")
                }()

                // Naughty - cleanup some ram!
                System.gc()
            }
        }

        System.exit(0)
    }
}