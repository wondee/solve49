
import groovy.transform.EqualsAndHashCode
import groovy.json.*

MAX = args[0] as int
ROOT_PATH = ".solved/"

jumpVectors = ([[-2, 2], [1, -1]].combinations() + [[-1, 1], [2, -2]].combinations()).collect {

	new Point(it)

}

def solve(startPoint) {
	stack = [[startPoint, []]]

	max = 0


	//(1..8).each {
	while (!stack.empty) {
		(actPoint, visited) = stack.pop()

		jumps = calculatePossibleJumps(actPoint, visited)

		/*
		println "------"
		println "actPoint: $actPoint"
		println "visited: ${visited.size()}"
		println "jumps: $jumps"
		*/

		if (jumps.empty) {
			n = visited.size() + 1
			if (max < n) {
				println "found new max: $n" 
				new File(ROOT_PATH + ("${MAX}_${n}.out")).write(new JsonBuilder(visited).toString())

				max = n
			}
		} else {
			visited << actPoint
			jumps.each {
				stack << [it, visited.collect()]
			}
		}
		//if (stack.size() % 1000 == 0) {
			//println "stack: ${stack.size()}"
			//println "stack: ${stack}"
		//}
	}

	println "max: $max"
}

def calculatePossibleJumps(actPoint, visited) {
	jumpVectors.collect {
		actPoint.add(it)
	}.findAll {
		it.isValid(MAX - 1) && !visited.contains(it)
	} 


}

@EqualsAndHashCode
class Point {

	final def x
	final def y

	def Point(List list) {
	 	x = list[0]
	 	y = list[1]
	}

	def add(other) {
		new Point([x + other.x, y + other.y])
	}

	def isValid(maxValue) {
		!(x < 0 || y < 0 || x > maxValue || y > maxValue)
	}
}

startPoint = [1, 2]

before = System.currentTimeMillis()
solve(new Point(startPoint))
println "took: " + (System.currentTimeMillis() - before) + " ms"

