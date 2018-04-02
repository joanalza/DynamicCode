library(ggplot2)

n <- length(list.dirs("//nas-csdm.rgu.ac.uk/csdm-H/Students/17/1715818/Desktop/Project/Code/DynamicPermutationOptimisation/plots/stirlingVSjosu/"))
for(z in 1:(n-1)){
  ##### GET DATA #####
  # Put instance results in the directory
  directory <- paste0("//nas-csdm.rgu.ac.uk/csdm-H/Students/17/1715818/Desktop/Project/Code/DynamicPermutationOptimisation/results/stirlingVSJosus/Cayley ",z,"/")
  setwd(paste0("//nas-csdm.rgu.ac.uk/csdm-H/Students/17/1715818/Desktop/Project/Code/DynamicPermutationOptimisation/plots/stirlingVSjosu/Cayley ",z,"/"))
  
  # Get instance names
  instances <- list.files(directory, pattern = "*.csv")
  
  sapply(instances, FUN = function(instance){
    
    df <- read.csv2(paste0(directory, instance))
    
    result.df <- as.data.frame(table(df))
    normalized <- result.df$Freq /(sum(result.df$Freq)/ length(unique(df$Method)))
    result.csv <- data.frame(result.df, normalized)
    
    write.csv2(result.csv, file = paste0("table_n",unique(result.csv$Size),"-cayley",unique(df$Cayley),".csv"))
  })
  
  
  ##### MAKE GRAPHS #####
  setwd(paste0("//nas-csdm.rgu.ac.uk/csdm-H/Students/17/1715818/Desktop/Project/Code/DynamicPermutationOptimisation/plots/stirlingVSjosu/Cayley ",z,"/"))
  
  files <- list.files(pattern = "*.csv")
  sizes <- sub("*-cayley..csv",'', sub("table_n*",'', files))
  cayley <- unique(sub(".csv","",sub("table_n[0-9]*-cayley*",'', files)))
  
  range.v <- sapply(files, FUN = function(file){
    df <- read.csv2(file)
    
    ggplot(df, aes(x= Permutation, y= normalized, group = Method, colour = Method)) + 
      geom_line() +
      scale_colour_manual(breaks=c("Josus", "Stirling"),
                          labels=c("Binary method", "Stirling numbers"),
                          values = c('Josus' = 'tomato', 'Stirling' = 'navy')) +
      labs(title = paste0("Probability of permutations using Cayley distance ", cayley),
           x = "Permutation", 
           y = "Probability") +
      theme(axis.text.x = element_text(angle = 90, hjust = 1), 
            panel.background = element_blank(), 
            panel.grid.major = element_line(colour = "#D8D8D8"), 
            panel.grid.minor = element_line(colour = "#F2F2F2"),
            legend.position=c(.83,.87),
            legend.background = element_rect(fill="#ccebff"),
            legend.key = element_rect(colour = "transparent", fill = "transparent"))
    
    ggsave( paste("plot_", sub(".csv", "", file),".pdf" ,sep = ""), plot = last_plot())
    return(c(range(subset(df, Method == "Stirling")$normalized),
             range(subset(df, Method == "Josus")$normalized)))
  })
  
  increasingBehaviour <- data.frame(t(range.v), row.names = sizes)
  increasingBehaviour <- increasingBehaviour[ order(as.numeric(row.names(increasingBehaviour))), ]
  colnames(increasingBehaviour)<- c("StirlingMin","StirlingMax","BinaryMin","BinaryMax")
  
  ggplot(increasingBehaviour, aes(x= sort(as.numeric(row.names(increasingBehaviour))), group = 1)) +
    labs(title = paste0("Performance of the probability when the permutation size increase with Cayley ",cayley),
         x = "Permutation size",
         y = "Range of the probability") +
    geom_line(aes(y = StirlingMax, color = "Stirling")) +
    geom_line(aes(y = StirlingMin, color = "Stirling")) +
    geom_ribbon(aes(ymin=StirlingMin,ymax=StirlingMax), fill="blue", alpha="0.3") +
    geom_line(aes(y = BinaryMin, color = "Binary")) +
    geom_line(aes(y = BinaryMax, color = "Binary")) +
    geom_ribbon(aes(ymin=BinaryMin,ymax=BinaryMax), fill="red", alpha="0.1") +
    scale_color_manual(name = "Method",
                       breaks=c("Binary", "Stirling"),
                       labels=c("Binary method", "Stirling numbers"),
                       values = c('Binary' = 'tomato', 'Stirling' = 'navy'))+
    theme(panel.background = element_blank(), 
          panel.grid.major = element_line(colour = "#D8D8D8"), 
          panel.grid.minor = element_line(colour = "#F2F2F2"),
          legend.position=c(.83,.87),
          legend.background = element_rect(fill="#ccebff"),
          legend.key = element_rect(colour = "transparent", fill = "transparent"))
  
  ggsave( paste0("increasingBehaviourPlot_Cayley",cayley,".pdf") , plot = last_plot())
}