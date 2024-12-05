require 'rgl/adjacency'
require 'rgl/topsort'

edges = []
updates = []

reading_status = 'order'
File.read('day5.txt').split("\n").map do |line|
  if line == ''
    reading_status = 'update'
  elsif reading_status == 'order'
    edges << line.split('|').map(&:to_i)
  else
    updates << line.split(',').map(&:to_i)
  end  
end

total_correct = 0
total_incorrect = 0

updates.each do |update|
  relevant_edges = edges.filter { |edge| update.include?(edge[0]) && update.include?(edge[1]) }
    
  graph = RGL::DirectedAdjacencyGraph.new
  
  relevant_edges.each do |edge|
    graph.add_edge(edge[0], edge[1])
  end

  order = graph.topsort_iterator.to_a

  if update == update.sort_by { |n| order.index(n) }    
    total_correct += update[update.size/2]
  else
    total_incorrect += update.sort_by { |n| order.index(n) }[update.size/2]
  end
end

p total_correct
p total_incorrect